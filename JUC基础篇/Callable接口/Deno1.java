package com.happy.juc.JUC基础篇.Callable接口;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

// 比较两个接口
// 实现Runnable接口
class MyThread1 implements Runnable {
  @Override
  public void run() {

  }
}

// 实现Callable接口
class MyThread2 implements Callable<Integer> {
  @Override
  public Integer call() throws Exception {
    System.out.println(Thread.currentThread().getName() + " come in callable");
    return 200;
  }
}

public class Deno1 {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // Runnable接口创建线程
    new Thread(new MyThread1(), "AA").start();

    // Callable接口创建线程
    // FutureTask
    FutureTask<Integer> task = new FutureTask<>(new MyThread2());

    int a = 122;
    // lam表达式
    FutureTask<Integer> task1 = new FutureTask<>(() -> {
      System.out.println(Thread.currentThread().getName() + " come in callable");
      return a;
    });
    // 创建一个线程
    new Thread(task1, "lucy").start();
    new Thread(task, "mary").start();

    // task1中的任务是否完成
//    while (!task1.isDone()) {
//      System.out.println("wait...");
//    }

    // 调用FutureTask的get方法
    System.out.println(task1.get());
    System.out.println(task.get());
    System.out.println(Thread.currentThread().getName() + " come over");



    // FutureTask 也叫 未来任务
  }
}
