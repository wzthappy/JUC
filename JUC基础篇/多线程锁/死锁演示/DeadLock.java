package com.happy.juc.JUC基础篇.多线程锁.死锁演示;

/**
 * 演示死锁
 */
public class DeadLock {

  // 创建两个对象
  static Object a = new Object();
  static Object b = new Object();

  public static void main(String[] args) {
    new Thread(() -> {
      synchronized (a) {
        System.out.println(Thread.currentThread().getName() + " 持有锁a，试图获取锁b");

        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }

        synchronized (b) {
          System.out.println(Thread.currentThread().getName() + " 获取锁b");
        }

      }
    }, "A").start();


    new Thread(() -> {
      synchronized (b) {
        System.out.println(Thread.currentThread().getName() + " 持有锁b，试图获取锁a");

        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }

        synchronized (a) {
          System.out.println(Thread.currentThread().getName() + " 获取锁a");
        }

      }
    }, "B").start();
  }
}
