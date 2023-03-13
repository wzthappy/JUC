package com.happy.juc.练习JUC;

import sun.misc.Unsafe;

public class a {
  public static void main(String[] args) {
    Unsafe unsafe = Unsafe.getUnsafe();
    unsafe.park(false, Integer.MAX_VALUE);


    unsafe.unpark(Thread.currentThread());
  }
}
