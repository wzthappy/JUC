package com.happy.juc.JUC基础篇.复习Synchronized.卖票;

import lombok.SneakyThrows;

/**
 * 第一步: 创建资源类，定义属性和操作方法
 */
class Ticket {
  // 票数
  private int number = 30;
  // 操作方法: 卖票
  public synchronized void sale() {
    // 判断: 是否有票
    if (number > 0) {
      System.out.println(Thread.currentThread().getName() +
          ": 卖出: " + (30 - --number) + " 剩下: " + (number));
    }
  }
}

public class SaleTicket {
  // 第二步: 创建多个线程，调用资源类的操作方法
  public static void main(String[] args) {
    // 创建Ticket对象
    Ticket ticket = new Ticket();
    // 创建三个线程
    new Thread(new Runnable() {
      @SneakyThrows
      @Override
      public void run() {
        // 调用卖票的方法
        for (int i = 0; i < 40; i++) {
          ticket.sale();
          Thread.sleep(100);
        }
      }
    }, "AA").start();

    new Thread(new Runnable() {
      @SneakyThrows
      @Override
      public void run() {
        // 调用卖票的方法
        for (int i = 0; i < 40; i++) {
          ticket.sale();
          Thread.sleep(100);
        }
      }
    }, "BB").start();

    new Thread(new Runnable() {
      @SneakyThrows
      @Override
      public void run() {
        // 调用卖票的方法
        for (int i = 0; i < 40; i++) {
          ticket.sale();
          Thread.sleep(100);
        }
      }
    }, "CC").start();
  }
}
