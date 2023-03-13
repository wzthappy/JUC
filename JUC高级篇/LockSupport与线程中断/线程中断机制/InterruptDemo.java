package com.happy.juc.JUC高级篇.LockSupport与线程中断.线程中断机制;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class InterruptDemo {
  static volatile boolean isStop = false;
  static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

  public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(() -> {
      while (true) {
        if (Thread.currentThread().isInterrupted()) {
          System.out.println(Thread.currentThread().getName() + "\t isInterrupted()被修改为true，程序停止");
          break;
        }
        System.out.println("-------hello interrupt api");
      }
    }, "t1");

    System.out.println("--------t1的默认中断标志位: " + t1.isInterrupted());

    t1.start();

    // 暂停毫秒
    TimeUnit.MILLISECONDS.sleep(20);

//    new Thread(() -> {
//      t1.interrupt();
//    }, "t2").start();
    t1.interrupt();
  }

  private static void m2_atomicBoolean() throws InterruptedException {
    new Thread(() -> {
      while (true) {
        if (atomicBoolean.get()) {
          System.out.println(Thread.currentThread().getName() + "\t atomicBoolean被修改为true，程序停止");
          break;
        }
        System.out.println("-------hello atomicBoolean");
      }
    }).start();

    // 暂停毫秒
    TimeUnit.MILLISECONDS.sleep(20);

    new Thread(() -> {
      atomicBoolean.set(true);
    }, "t2").start();
  }

  private static void m1_voletatile() throws InterruptedException {
    new Thread(() -> {
      while (true) {
        if (isStop) {
          System.out.println(Thread.currentThread().getName() + "\t isStop被修改为true，程序停止");
          break;
        }
        System.out.println("-------hello volatile");
      }
    }).start();

    // 暂停毫秒
    TimeUnit.MILLISECONDS.sleep(20);

    new Thread(() -> {
      isStop = true;
    }, "t2").start();
  }
}
