package com.happy.juc.练习JUC.实现自旋锁;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 实现 自旋锁
 */
public class SpinLockDemo {
  static AtomicReference<Thread> atomicReference = new AtomicReference<>();

  public static void myLock() {
    System.out.println(Thread.currentThread().getName() + "尝试获取锁");
    while (!atomicReference.compareAndSet(null, Thread.currentThread())) {

    }
    System.out.println(Thread.currentThread().getName() + "获取成功");
  }

  public static void myUnLock() {
    atomicReference.compareAndSet(Thread.currentThread(), null);
    System.out.println(Thread.currentThread().getName() + "释放锁");
  }


  public static void main(String[] args) {
    new Thread(() -> {
      myLock();
      try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {throw new RuntimeException(e);}
      myUnLock();
    }, "A").start();
    try {TimeUnit.MILLISECONDS.sleep(3);} catch (InterruptedException e) {throw new RuntimeException(e);}
    new Thread(() -> {
      myLock();

      myUnLock();
    }, "B").start();
  }
}
