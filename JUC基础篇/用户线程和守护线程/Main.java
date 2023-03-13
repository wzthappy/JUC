package com.happy.juc.JUC基础篇.用户线程和守护线程;

public class Main {
  public static void main(String[] args) {

    Thread aa = new Thread(() -> {
      System.out.println(Thread.currentThread().getName() + "::" +
          Thread.currentThread().isDaemon());
      // Thread.currentThread().isDaemon()  true表示守护线程、false表示用户线程
      while (true) {

      }
    }, "aa");
    // 用户线程(默认)   用户线程: 主线程结束了，用户线程还在运行，JVM存活
//    aa.setDaemon(false);

    // 设置成守护线程   守护线程: 没有用户线程了，都是守护线程，JVM结束
    aa.setDaemon(true);

    aa.start();
    System.out.println(Thread.currentThread().getName() + " over");
  }
}
