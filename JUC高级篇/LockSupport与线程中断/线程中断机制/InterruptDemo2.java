package com.happy.juc.JUC高级篇.LockSupport与线程中断.线程中断机制;

import java.util.concurrent.TimeUnit;

public class InterruptDemo2 {
  public static void main(String[] args) throws InterruptedException {
    // 实例方法interrupt() 仅仅是设置线程的中断状态设置为true，不会停止线程
    Thread t1 = new Thread(() -> {
      for (int i = 1; i <= 300; i++) {
        System.out.println("-----: " + i);
      }
      System.out.println("t1线程调用interrupt()后的中断标识02: " +
          Thread.currentThread().isInterrupted());
    }, "t1");
    t1.start();

    System.out.println("t1线程默认的中断标识: " + t1.isInterrupted());

    // 暂停毫秒
    TimeUnit.MILLISECONDS.sleep(2);
    t1.interrupt();
    System.out.println("t1线程调用interrupt()后的中断标识01: " + t1.isInterrupted());

    TimeUnit.MILLISECONDS.sleep(2000);
    System.out.println("t1线程调用interrupt()后的中断标识03: " + t1.isInterrupted());
  }
}
