package com.happy.juc.JUC高级篇.CompletableFuture.CompletableFuture常用方法;

import java.util.concurrent.*;

public class CompletableFutureAPI2Demo {
  public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

    CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      System.out.println("1111");
      return 1;
    }, fixedThreadPool).handle((f, e) -> {
//      int i = 10 / 0;
      System.out.println("2222");
      return f + 2;
    }).handle((f, e) -> {
      System.out.println("3333");
      return f + 3;
    }).whenComplete((v, e) -> {
      if (e == null) {
        System.out.println("------计算结果: " + v);
      }
    }).exceptionally(e -> {
      e.printStackTrace();
      System.out.println(e.getMessage());
      return null;
    });

    System.out.println(Thread.currentThread().getName() + "------主线程先去忙其他任务");

    fixedThreadPool.shutdown();
  }
}
