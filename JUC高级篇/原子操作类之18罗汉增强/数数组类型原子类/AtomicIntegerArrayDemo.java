package com.happy.juc.JUC高级篇.原子操作类之18罗汉增强.数数组类型原子类;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayDemo {
  public static void main(String[] args) {
    AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[5]); // 0 0 0 0 0
//    AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[]{1, 2, 3, 4, 5});

    for (int i = 0; i < atomicIntegerArray.length(); i++) {
      System.out.println(atomicIntegerArray.get(i));
    }

    System.out.println();

    int tmpInt = 0;

    tmpInt = atomicIntegerArray.getAndSet(0, 1122);
    System.out.println(tmpInt + "\t" + atomicIntegerArray.get(0));

    tmpInt = atomicIntegerArray.getAndIncrement(0);
    System.out.println(tmpInt + "\t" + atomicIntegerArray.get(0));
  }
}
