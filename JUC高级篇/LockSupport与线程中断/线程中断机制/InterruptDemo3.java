package com.happy.juc.JUC高级篇.LockSupport与线程中断.线程中断机制;

import java.util.concurrent.TimeUnit;

public class InterruptDemo3 {
  public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(() -> {
      while (true) {
        if (Thread.currentThread().isInterrupted()) {
          System.out.println(Thread.currentThread().getName() +
              "\t 中断标志位: " + Thread.currentThread().isInterrupted() +
              " 程序停止");
          break;
        }
        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt(); // 为什么要在异常处，在调用一次??
          e.printStackTrace();
        }
        System.out.println("------ hello InterruptDemo3");
      }
    }, "t1");
    t1.start();

    // 暂停几秒钟线程
    TimeUnit.SECONDS.sleep(1);

    new Thread(t1::interrupt, "t2").start();
  }
}
