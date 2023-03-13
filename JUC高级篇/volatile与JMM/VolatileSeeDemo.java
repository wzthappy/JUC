package com.happy.juc.JUC高级篇.volatile与JMM;

import java.util.concurrent.TimeUnit;

public class VolatileSeeDemo {
//  static  boolean flag = true;
  static volatile boolean flag = true;

  public static void main(String[] args) throws InterruptedException {
    new Thread(() -> {
      System.out.println(Thread.currentThread().getName() + "\t -----come in");
      while (flag) {
//        System.out.println(flag);
      }
      System.out.println(Thread.currentThread().getName() + "\t -----flag被设置为false，程序停止");
    }, "t1").start();

    TimeUnit.SECONDS.sleep(2);

    flag = false;

    System.out.println(Thread.currentThread().getName() + "\t 修改完成flag: " + flag);
  }
}
