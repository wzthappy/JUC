package com.happy.juc.JUC高级篇.CompletableFuture;

import java.util.concurrent.*;

public class CompletableFutureBuildDemo {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3); // 线程池

    // 无返回值
    CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
      System.out.println(Thread.currentThread().getName());
      // 暂停几秒中线程
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }, fixedThreadPool);

    System.out.println(completableFuture.get());  // null

    //  有返回值
    CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
      System.out.println(Thread.currentThread().getName());
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      return "hello supplyAsync";
    }, fixedThreadPool);

    System.out.println(completableFuture1.get());  // hello supplyAsync


    fixedThreadPool.shutdown();  // 关闭线程池
  }
}
