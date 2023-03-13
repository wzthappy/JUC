package com.happy.juc.JUC高级篇.Synchronized与锁升级.轻量锁;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author happy
 */
public class SynchronizedUpDemo {
  public static void main(String[] args) {
    Object o = new Object();

    new Thread(() -> {
      synchronized (o) {
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
      }
    }, "t1").start();

  }
}
