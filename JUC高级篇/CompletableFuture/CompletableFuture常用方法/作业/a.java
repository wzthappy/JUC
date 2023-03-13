package com.happy.juc.JUC高级篇.CompletableFuture.CompletableFuture常用方法.作业;

import java.util.concurrent.CompletableFuture;

public class a {
  public static void main(String[] args) {
    CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
      System.out.println(Thread.currentThread().getName() + "\t ---- come in 1");
      return 10;
    }).thenCombine(CompletableFuture.supplyAsync(() -> {
      System.out.println(Thread.currentThread().getName() + "\t ---- come in 2");
      return 20;
    }), (x, y) -> {
      System.out.println(Thread.currentThread().getName() + "\t ---- come in 3");
      return x + y;
    }).thenCombine(CompletableFuture.supplyAsync(() -> {
      System.out.println(Thread.currentThread().getName() + "\t ---- come in 4");
      return 30;
    }), (a, b) -> {
      System.out.println(Thread.currentThread().getName() + "\t ---- come in 5");
      return a + b;
    });

    System.out.println("---- 主线程结束，END");
    System.out.println(completableFuture.join());
  }
}
