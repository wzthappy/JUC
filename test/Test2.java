package com.happy.juc.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j(topic = "c.Test2")
public class Test2 {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        log.debug("running...");
        Thread.sleep(2000);
        return 100;
      }
    });

    Thread t = new Thread(task, "张三");
    t.start();

    Thread.sleep(4000);


    Integer integer = task.get();
    log.debug("{}", integer);
  }
}
