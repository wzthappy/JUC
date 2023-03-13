package com.happy.juc.JUC高级篇.LockSupport与线程中断.LockSupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class LockSupportDemo {
  public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(() -> {
      System.out.println(Thread.currentThread().getName() + "\t " +
          "-----come in");
      LockSupport.park();
      System.out.println(Thread.currentThread().getName() + "\t " +
          "-----被唤醒");
    }, "t1");
    t1.start();

    // 暂停几秒钟线程
    TimeUnit.SECONDS.sleep(1);

    new Thread(() -> {
      System.out.println(Thread.currentThread().getName() + "\t " +
          "-----发出通知");
      LockSupport.unpark(t1);
    }).start();
  }

  private static void lockAwaitSignal() throws InterruptedException {
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    new Thread(() -> {
      lock.lock();

      try {
        System.out.println(Thread.currentThread().getName() + "\t " +
            "-----come in");
        condition.await();
        System.out.println(Thread.currentThread().getName() + "\t " +
            "-----被唤醒");
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }, "t1").start();

    // 暂停几秒钟线程
    TimeUnit.SECONDS.sleep(1);

    new Thread(() -> {
      lock.lock();
      try {
        condition.signal();
        System.out.println(Thread.currentThread().getName() + "\t " +
            "-----发出通知");
      } finally {
        lock.unlock();
      }
    }, "t2").start();
  }

  private static void syncWaitNotify() {
    Object objectLock = new Object();

    new Thread(() -> {
      try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
      synchronized (objectLock) {
        System.out.println(Thread.currentThread().getName() + "\t " +
            "-----come in");
        try {
          objectLock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t" +
            " ----被唤醒");
      }
    }, "t1").start();

//    TimeUnit.SECONDS.sleep(1);

    new Thread(() -> {
      synchronized (objectLock) {
        objectLock.notify();
        System.out.println(Thread.currentThread().getName() + "\t " +
            "----发出通知");
      }
    }, "t2").start();
  }
}
