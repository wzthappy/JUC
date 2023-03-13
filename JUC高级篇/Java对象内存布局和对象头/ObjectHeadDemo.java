package com.happy.juc.JUC高级篇.Java对象内存布局和对象头;

/**
 * @author happy
 */
public class ObjectHeadDemo {
  public static void main(String[] args) {
    Object o = new Object(); // new一个对象，占内存多少？

    System.out.println(o.hashCode()); // 这个hashcode记录在对象的什么地方?

    synchronized (o) {

    }

    System.gc(); // 手动收集垃圾.....，15次可以从新生代--到-->养老区

    Customer c1 = new Customer();
  }
}

class Customer {
  int id;
  String customerName;
}
