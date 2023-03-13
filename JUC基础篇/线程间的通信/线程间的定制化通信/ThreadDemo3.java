package com.happy.juc.JUC基础篇.线程间的通信.线程间的定制化通信;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 第一步: 创建一个资源类
 */
class ShareResource {
  // 定义标志位
  private int flag = 1; // 1. AA   2. BB   3. CC
  // 创建Lock锁
  private final Lock lock = new ReentrantLock();

  // 创建三个Condition
  private final Condition c1 = lock.newCondition();
  private final Condition c2 = lock.newCondition();
  private final Condition c3 = lock.newCondition();

  // 定义5次，参数第几轮
  public void print5(int loop) throws InterruptedException {
    lock.lock(); // 上锁
    try {
      // 判断
      while (flag != 1) {
        // 等待
        c1.await();
      }
      // 干活
      for (int i = 1; i <= 5; i++) {
        System.out.println(Thread.currentThread().getName() + " :: " + i + " : 轮数 : " + loop);
      }
      // 通知
      flag = 2;
      c2.signalAll(); // 通知c2的等待的线程
    } finally {
      lock.unlock(); // 解锁
    }
  }

  // 打印10次
  public void print10(int loop) throws InterruptedException {
    lock.lock(); // 上锁
    try {
      // 判断
      while (flag != 2) {
        c2.await();
      }
      // 干活
      for (int i = 1; i <= 10; i++) {
        System.out.println(Thread.currentThread().getName() + " :: " + i + " : 轮数 : " + loop);
      }
      // 通知
      flag = 3;
      c3.signalAll();
    } finally {
      lock.unlock(); // 上锁
    }
  }

  // 定义15次
  public void print15(int loop) throws InterruptedException {
    lock.lock();
    try {
      while (flag != 3) {
        c3.await();
      }
      for (int i = 1; i <= 15; i++) {
        System.out.println(Thread.currentThread().getName() + " :: " + i + " : 轮数 :" + loop);
      }
      flag = 1;
      c1.signalAll();
    } finally {
      lock.unlock();
    }
  }
}

public class ThreadDemo3 {
  public static void main(String[] args) {
    ShareResource shareResource = new ShareResource();

    new Thread(() -> {
      for (int i = 1; i <= 5; i++) {
        try {
          shareResource.print5(i);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }, "AA").start();

    new Thread(() -> {
      for (int i = 1; i <= 5; i++) {
        try {
          shareResource.print10(i);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }, "BB").start();

    new Thread(() -> {
      for (int i = 1; i <= 5; i++) {
        try {
          shareResource.print15(i);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }, "CC").start();
  }
}
