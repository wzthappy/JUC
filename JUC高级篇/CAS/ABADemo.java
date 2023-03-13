package com.happy.juc.JUC高级篇.CAS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {
  static AtomicReference atomicReference = new AtomicReference(100);

  static AtomicStampedReference<Integer> stampedReference =
      new AtomicStampedReference<>(100, 1);

  public static void main(String[] args) {
    new Thread(() -> {
      int stamp = stampedReference.getStamp();
      System.out.println(Thread.currentThread().getName() + "\t" + "首次版本号: " + stamp);
      // 暂停500毫秒,保证后面的t4线程初始化拿到的版本号和我一样
      try {TimeUnit.MILLISECONDS.sleep(500);} catch (InterruptedException e) {throw new RuntimeException(e);}

      stampedReference.compareAndSet(100, 101,
          stampedReference.getStamp(), stampedReference.getStamp() + 1);
      System.out.println(Thread.currentThread().getName() + "\t" + "2次流水号: " +
          stampedReference.getStamp());

      stampedReference.compareAndSet(101, 100,
          stampedReference.getStamp(), stampedReference.getStamp() + 1);
      System.out.println(Thread.currentThread().getName() + "\t" + "3次流水号: " +
          stampedReference.getStamp());
    }, "t3").start();

    new Thread(() -> {
      int stamp = stampedReference.getStamp();
      System.out.println(Thread.currentThread().getName() + "\t" + "首次版本号: " + stamp);

      // 暂停1秒钟线程,等待上面的t3线程,发生了ABA问题
      try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}

      boolean b = stampedReference
          .compareAndSet(100, 2022, stamp, stamp + 1);
      System.out.println(b + "\t" + stampedReference.getReference() + "\t" +
          stampedReference.getStamp());
    }, "t4").start();
  }

  private static void abaHappen() {
    new Thread(() -> {
      atomicReference.compareAndSet(100, 101);
      // 暂停毫秒
      try {TimeUnit.MILLISECONDS.sleep(10);} catch (InterruptedException e) {throw new RuntimeException(e);}
      atomicReference.compareAndSet(101, 100);
    }, "t1").start();

    new Thread(() -> {
      // 暂停毫秒
      try {TimeUnit.MILLISECONDS.sleep(200);} catch (InterruptedException e) {throw new RuntimeException(e);}
      System.out.println(atomicReference.compareAndSet(100, 2022) + "\t" +
          atomicReference.get());
    }, "t2").start();
  }
}
