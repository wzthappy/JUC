package com.happy.juc.JUC基础篇.线程间的通信.加1减1.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 第一步 创建资源类，定义属性和操作方法
 */

class Share {
  private int number = 0;
  private final Lock lock = new ReentrantLock();
  private final Condition condition = lock.newCondition();

  // +1
  public void incr () {
    lock.lock(); // 上锁
    try {
      // 判断
      while (number != 0) {
        condition.await(); // 等待
      }
      number++;
      System.out.println(Thread.currentThread().getName() + " :: " + number);
      // 通知其他线程
      condition.signalAll();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      lock.unlock(); // 解锁
    }
  }

  // -1
  public void decr () {
    lock.lock(); // 上锁
    try {
      // 判断
      while (number != 1) {
        condition.await(); // 等待
      }
      number--;
      System.out.println(Thread.currentThread().getName() + " :: " + number);
      // 通知其他线程
      condition.signalAll();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      lock.unlock(); // 解锁
    }
  }
}

public class ThreadDemo2 {
  public static void main(String[] args) {
    Share share = new Share();

    new Thread(() -> {
      for (int i = 0; i < 10; i++) {
        share.incr();
      }
    }, "AA").start();

    new Thread(() -> {
      for (int i = 0; i < 10; i++) {
        share.decr();
      }
    }, "BB").start();

    new Thread(() -> {
      for (int i = 0; i < 10; i++) {
        share.incr();
      }
    }, "CC").start();

    new Thread(() -> {
      for (int i = 0; i < 10; i++) {
        share.decr();
      }
    }, "DD").start();
  }
}
