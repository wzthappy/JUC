package com.happy.juc.JUC基础篇.JUC强大的辅助类.循环栅栏CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

// 集齐7颗龙珠就可以召唤神龙
public class CyclicBarrierDemo {
  // 创建固定值
  private static final int NUMBER = 7;
  public static void main(String[] args) {
    // 创建CyclicBarrier
    CyclicBarrier cyclicBarrier = new CyclicBarrier(NUMBER, () -> {
      System.out.println("集齐7颗龙珠就可以召唤神龙");
    });

    // 集齐七颗龙珠过程
    for (int i = 1; i <= 7; i++) {
      new Thread(() -> {
        try {
          System.out.println(Thread.currentThread().getName() + " 星龙珠被收集到了");
          // 等待
          cyclicBarrier.await();
          System.out.println("---------------");
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }, String.valueOf(i)).start();
    }
  }
}
