package com.happy.juc.JUC高级篇.Synchronized与锁升级.无锁;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author happy
 */
public class SynchronizedUpDemo {
  public static void main(String[] args) {
    Object o = new Object();

    System.out.println("10进制: " + o.hashCode());
    System.out.println("16进制: " + Integer.toHexString(o.hashCode()));
    System.out.println("2进制: " + Integer.toBinaryString(o.hashCode()));

    System.out.println(ClassLayout.parseInstance(o).toPrintable());
  }
}
