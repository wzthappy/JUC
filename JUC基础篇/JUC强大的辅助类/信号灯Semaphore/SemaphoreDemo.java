package com.happy.juc.JUC基础篇.JUC强大的辅助类.信号灯Semaphore;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

// 6辆汽车，等3个车位
public class SemaphoreDemo {
  public static void main(String[] args) {
    // 创建Semaphore，设置许可数量
    Semaphore semaphore = new Semaphore(3);

    // 模拟6辆汽车
    for (int i = 1; i <= 6; i++) {
      new Thread(() -> {
        try {
          // 抢占
          semaphore.acquire();
          System.out.println(Thread.currentThread().getName() + " 抢到了车位");

          // 设置随机停车时间
          TimeUnit.SECONDS.sleep(new Random().nextInt(5));

          System.out.println(Thread.currentThread().getName() + " ---- 离开车位");
        } catch (Exception e) {
          throw new RuntimeException(e);
        } finally {
          // 释放
          semaphore.release();
        }
      }, String.valueOf(i)).start();
    }
  }
}
