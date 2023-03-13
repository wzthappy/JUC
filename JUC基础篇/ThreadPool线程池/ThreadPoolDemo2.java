package com.happy.juc.JUC基础篇.ThreadPool线程池;

import java.util.concurrent.*;

// 自定义线程池创建
public class ThreadPoolDemo2 {
  public static void main(String[] args) {
    ExecutorService threadPool = new ThreadPoolExecutor(
        2,
        5,
        2L, TimeUnit.SECONDS,
        new ArrayBlockingQueue<>(3),
        Executors.defaultThreadFactory(),
        new ThreadPoolExecutor.AbortPolicy()
    );

    // 10 个顾客请求
    try {
      for (int i = 1; i <= 20; i++) {
        // 执行
        threadPool.execute(() -> {
          System.out.println(Thread.currentThread().getName() + " 办理业务");
        });
      }
    } finally {
      // 关闭线程池
      threadPool.shutdown();
    }
  }
}
