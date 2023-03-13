package com.happy.juc.JUC高级篇.CompletableFuture.CompletableFuture常用方法;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureAPI4Demo {
  public static void main(String[] args) {
    CompletableFuture<String> playA = CompletableFuture.supplyAsync(() -> {
      try {
        System.out.println("A come in");
        // 暂停几秒种线程
        TimeUnit.SECONDS.sleep(3);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      return "playA";
    });

    CompletableFuture<String> playB = CompletableFuture.supplyAsync(() -> {
      try {
        System.out.println("B come in");
        // 暂停几秒种线程
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      return "playB";
    });


    CompletableFuture<String> r = playA.applyToEither(playB, f -> {
      return f + " is winer";
    });

    System.out.println(Thread.currentThread().getName() + "\t--------: " + r.join());
  }
}
