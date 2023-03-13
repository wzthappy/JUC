package com.happy.juc.JUC高级篇.Java对象内存布局和对象头;

import org.openjdk.jol.info.ClassLayout;

public class JOLDemo {
  public static void main(String[] args) {
//    Thread.currentThread
//    System.out.println(VM.current().details());
//    System.out.println(VM.current().objectAlignment());
    Object o = new Object();

    System.out.println(ClassLayout.parseInstance(o).toPrintable());
  }
}

class Customer1 {
  int id;
  boolean flag = false;
}
