package com.happy.juc.JUC高级篇.CompletableFuture;

import java.util.concurrent.*;

public class CompletableFutureUseDemo {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService threadPool = Executors.newFixedThreadPool(3);// 线程池

    try {
      CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
        System.out.println(Thread.currentThread().getName() + "----come in");
        int result = ThreadLocalRandom.current().nextInt(10);
        // 暂停几秒线程
        try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
        System.out.println("------1秒中后出结果: " + result);
        int i = 10 / 0;
        return result;
      }, threadPool).whenComplete((v, e) -> {
        if (e == null) {
          System.out.println("-----计算完成，更新系统UpdateValue: " + v);
        }
      }).exceptionally(e -> {
        e.printStackTrace();
        System.out.println("异常情况: " + e.getCause() + "\t" + e.getMessage());
        return null;
      });

      System.out.println(Thread.currentThread().getName() + "线程先去忙其他任务");
    } finally {
      threadPool.shutdown();
    }

    // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭
//    try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {throw new RuntimeException(e);}
//    System.out.println("-----------");
  }

  private static void future1() throws InterruptedException, ExecutionException {
    CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
      System.out.println(Thread.currentThread().getName() + "-----come in");
      int result = ThreadLocalRandom.current().nextInt(10);
      // 暂停几秒线程
      try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
      System.out.println("------1秒中后出结果: " + result);
      return result;
    });

    System.out.println(Thread.currentThread().getName() + "线程先去");

    System.out.println(completableFuture.get());
  }
}
