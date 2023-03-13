package com.happy.juc.JUC高级篇.CompletableFuture.CompletableFuture常用方法;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWithThreadPoolDemo {
  public static void main(String[] args) throws InterruptedException {
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

    try {
      CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
        try {TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {throw new RuntimeException(e);}
        System.out.println("1号任务" + "\t" + Thread.currentThread().getName());
        return "abcd";
      }, fixedThreadPool).thenRunAsync(() -> {
        try {TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {throw new RuntimeException(e);}
        System.out.println("2号任务" + "\t" + Thread.currentThread().getName());
      }).thenRun(() -> {
        try {TimeUnit.MILLISECONDS.sleep(10);} catch (InterruptedException e) {throw new RuntimeException(e);}
        System.out.println("3号任务" + "\t" + Thread.currentThread().getName());
      }).thenRun(() -> {
        try {TimeUnit.MILLISECONDS.sleep(10);} catch (InterruptedException e) {throw new RuntimeException(e);}
        System.out.println("4号任务" + "\t" + Thread.currentThread().getName());
      });

      System.out.println(completableFuture.get());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      fixedThreadPool.shutdown();
    }
  }
}
