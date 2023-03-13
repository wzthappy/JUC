package com.happy.juc.JUC高级篇.CompletableFuture.CompletableFuture常用方法;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompletableFutureAPIDemo {
  public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
    CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
      // 暂停几秒种线程
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      return "abc";
    });

    // 1. 获取结果和触发计算:
//    System.out.println(completableFuture.get());
//    System.out.println(completableFuture.get(2L, TimeUnit.SECONDS));
//    System.out.println(completableFuture.join());
    TimeUnit.SECONDS.sleep(2);
//    System.out.println(completableFuture.getNow("我是线程没有完成的默认值"));

    System.out.println(completableFuture
        .complete("completeValue") + "\t"
        + completableFuture.join());

  }
}
