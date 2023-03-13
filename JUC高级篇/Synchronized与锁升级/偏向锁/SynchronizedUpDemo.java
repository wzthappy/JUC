package com.happy.juc.JUC高级篇.Synchronized与锁升级.偏向锁;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * @author happy
 */
public class SynchronizedUpDemo {
  public static void main(String[] args) throws InterruptedException {
    TimeUnit.SECONDS.sleep(5); // 后台开启偏向锁默认有4秒钟的延迟
    // biased
    Object o = new Object();

    System.out.println(ClassLayout.parseInstance(o).toPrintable());

    System.out.println("=================");

    new Thread(() -> {
      synchronized (o) {
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
      }
    }).start();

//    synchronized (o) {
//      System.out.println(ClassLayout.parseInstance(o).toPrintable());
//    }
  }
}
