package com.happy.juc.JUC高级篇.原子操作类之18罗汉增强.引用类型原子类;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class AtomicMarkableReferenceDemo {
  static AtomicMarkableReference<Integer> markableReference =
      new AtomicMarkableReference<Integer>(100, false);

  public static void main(String[] args) {
    new Thread(() -> {
      boolean marked = markableReference.isMarked();
      System.out.println(Thread.currentThread().getName() + "\t" + "默认标识: " + marked);
      // 停止1秒钟，等待后面的T2线程拿到和我一样的模式flag标识，都是false
      try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
      markableReference.compareAndSet(100, 1000,
          marked, !marked);
    }, "t1").start();

    new Thread(() -> {
      boolean marked = markableReference.isMarked();
      System.out.println(Thread.currentThread().getName() + "\t" + "默认标识: " + marked);
      // 停止2秒钟
      try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {throw new RuntimeException(e);}
      boolean b = markableReference.compareAndSet(100, 2000,
          marked, !marked);
      System.out.println(Thread.currentThread().getName() + "\t" + "t2线程CASresult: " + b);
      System.out.println(Thread.currentThread().getName() + "\t" + markableReference.isMarked());
      System.out.println(Thread.currentThread().getName() + "\t" + markableReference.getReference());
    }, "t2").start();


  }
}
