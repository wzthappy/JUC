package com.happy.juc.JUC基础篇.线程间的通信.加1减1.Synchronized;

// 第一步 创建资源类，定义属性和操作方法
class Share {
  // 初始值
  private volatile int number = 0;

  // +1 的方法
  public synchronized void incr() throws InterruptedException {
    // 第二步 判断 干活 通知
    while (number != 0) { // 判断number值是否是0，如果不是0，等待
      this.wait(); // 这个方法有个特点，在哪里睡就在哪里醒
    }
    // 如果number值是0，就加1
    number++;
    System.out.println(Thread.currentThread().getName() + " :: " + number);
    // 通知其他线程
    this.notifyAll();
  }

  // -1 的方法
  public synchronized void decr() throws InterruptedException {
    while (number != 1) {
      this.wait();
    }
    // 如果number值是1，就减1
    number--;
    System.out.println(Thread.currentThread().getName() + " :: " + number);
    // 通知其他用户
    this.notifyAll();
  }
}

public class ThreadDemo1 {
  public static void main(String[] args) {
    // 第三步 创建多个线程，调用资源类的操作方法
    Share share = new Share();
    // 创建线程
    new Thread(() -> {
      for (int i = 0; i < 10; i++) {
        try {
          share.incr(); // +1
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }, "AA").start();

    new Thread(() -> {
      for (int i = 0; i < 10; i++) {
        try {
          share.decr(); // -1
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }, "BB").start();

    new Thread(() -> {
      for (int i = 0; i < 10; i++) {
        try {
          share.incr(); // -1
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }, "CC").start();

    new Thread(() -> {
      for (int i = 0; i < 10; i++) {
        try {
          share.decr(); // -1
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }, "DD").start();
  }
}
