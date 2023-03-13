package com.happy.juc.JUC基础篇.ThreadPool线程池;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 演示线程池三种常用分类
public class ThreadPoolDemo1 {
  public static void main(String[] args) {
    // 一池五线程
    ExecutorService threadpool1 = Executors.newFixedThreadPool(5);

    // 一池一线程
    ExecutorService threadpool2 = Executors.newSingleThreadExecutor();

    // 一池可扩容线程
    ExecutorService threadpool3 = Executors.newCachedThreadPool();


    // 10 个顾客请求
    try {
      for (int i = 1; i <= 20; i++) {
        // 执行
        threadpool3.execute(() -> {
          System.out.println(Thread.currentThread().getName() + " 办理业务");
        });
      }
    } finally {
      // 关闭线程池
      threadpool3.shutdown();
    }
  }
}
