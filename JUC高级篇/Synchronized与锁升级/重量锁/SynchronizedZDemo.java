package com.happy.juc.JUC高级篇.Synchronized与锁升级.重量锁;

import org.openjdk.jol.info.ClassLayout;

public class SynchronizedZDemo {
  public static void main(String[] args) {
    Object o = new Object();

    for (int i = 0; i < 100; i++) {
      new Thread(() -> {
        synchronized (o) {
          System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
      }, String.valueOf(i)).start();
    }
  }
}
