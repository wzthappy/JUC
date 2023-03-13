package com.happy.juc.JUC高级篇.CompletableFuture.CompletableFuture常用方法;

import java.util.concurrent.*;

public class CompletableFutureAPI3Demo {
  public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
//    CompletableFuture.supplyAsync(() -> {
//      return 1;
//    }).thenApply(f -> {
//      return f + 2;
//    }).thenApply(f -> {
//      return f + 3;
//    }).thenAccept(r -> {
//      System.out.println(r);
//    });

    System.out.println(CompletableFuture.supplyAsync(() -> "resultA")
        .thenRun(() -> {}).join());  // null

    System.out.println(CompletableFuture.supplyAsync(() -> "resultA")
        .thenAccept(r -> System.out.println(r)).join());  // resultA    null

    System.out.println(CompletableFuture.supplyAsync(() -> "resultA")
        .thenApply(r -> r + "resultB").join());  // resultAresultB
  }
}
