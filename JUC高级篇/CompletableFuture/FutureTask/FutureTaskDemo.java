package com.happy.juc.JUC高级篇.CompletableFuture.FutureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskDemo {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    FutureTask<String> futureTask = new FutureTask<>(new MyThread());
    new Thread(futureTask).start();

    System.out.println(futureTask.get());
  }
}

class MyThread implements Callable<String> {
  @Override
  public String call() throws Exception {
    System.out.println("-------- come in call() ");
    return "hello Callable";
  }
}