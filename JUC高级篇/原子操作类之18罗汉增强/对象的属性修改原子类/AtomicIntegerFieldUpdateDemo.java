package com.happy.juc.JUC高级篇.原子操作类之18罗汉增强.对象的属性修改原子类;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

class BankAccount { // 资源类
  String bankName = "CCB";

  public volatile int money = 0; // 钱数

  public void add () {
    money++;
  }

  // 因为对象的属性修改类型原子类都是抽象类，所以每次使用都必须使用静态方法newUpdater()创建一个更新器
  //    ，并且需要设置想要更新的类和属性
  AtomicIntegerFieldUpdater<BankAccount> fieldUpdater =
      AtomicIntegerFieldUpdater.newUpdater(BankAccount.class, "money");

  // 不加synchronized，保证高性能原子性，局部微创小手术
  public void transMoney (BankAccount bankAccount) {
    fieldUpdater.getAndIncrement(bankAccount);
  }
}

/**
 * 以一种线程安全的方式操作非线程安全对象的某些字段.
 *
 * 需求:
 *  10个线程，
 *  每个线程转账1000，
 *  使用synchronized,尝试使用AtomicIntegerFieldUpdater来实现。
 */
public class AtomicIntegerFieldUpdateDemo {
  public static void main(String[] args) throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(10);
    BankAccount bankAccount = new BankAccount();

    for (int i = 1; i <= 10; i++) {
      new Thread(() -> {
        try {
          for (int j = 1; j <= 1000; j++) {
//            bankAccount.add();
            bankAccount.transMoney(bankAccount);
          }
        } finally {
          countDownLatch.countDown(); // --
        }
      }, String.valueOf(i)).start();
    }

    countDownLatch.await(); // 堵塞，直到countDownLatch 为 0或小于0
    System.out.println(Thread.currentThread().getName() + "\t"
        + "result: " + bankAccount.money);
  }
}
