package com.happy.juc.JUC基础篇.JUC强大的辅助类.减少计数CountDownLatch;

import java.util.concurrent.CountDownLatch;

// 演示  CountDownLatch
public class CountDownLatchDemo {
  // 6个同学陆续离开教室之后，班长锁门
  public static void main(String[] args) throws InterruptedException {
    // 创建CountDownLatch
    CountDownLatch countDownLatch = new CountDownLatch(6);

    // 六个同学路线离开教师之后
    for (int i = 1; i <= 6; i++) {
      new Thread(() -> {
        System.out.println(Thread.currentThread().getName() + " 号同学离开了教室");
        // 计数器  -1
        countDownLatch.countDown();
      }, String.valueOf(i)).start();
    }

    // 等待
    countDownLatch.await();
    System.out.println(Thread.currentThread().getName() + " 班长锁门走人了");
  }
}
