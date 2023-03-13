package com.happy.juc.JUC基础篇.CompletableFuture异步回调;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo {


  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // 异步调用  没有返回值
    CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
      System.out.println(Thread.currentThread().getName() + " completableFuture1");
    }); // 会自动执行

    completableFuture1.get(); // 只有completableFuture1线程执行完毕才会执行之后的代码

    // 异步调用  有放回值
    CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
      System.out.println(Thread.currentThread().getName() + " completableFuture2");
//      try {
//        TimeUnit.SECONDS.sleep(2);
//      } catch (InterruptedException e) {
//        throw new RuntimeException(e);
//      }
      // 模拟异常
//      int i = 1 / 0;
      return 1024;
    });  // 会自动执行

    completableFuture2.whenComplete((t, u) -> {
      System.out.println("------t = " + t); // 方法的返回值
      System.out.println("------u = " + u); // 方法中异常信息
    })
        .get();  // 只有completableFuture1线程执行完毕才会执行之后的代码，get可不加


    System.out.println("000000000000");
    TimeUnit.SECONDS.sleep(3);
  }
}
