package com.happy.juc.JUC高级篇.CompletableFuture.FutureTask;

import java.util.concurrent.*;

public class FutureThreadPoolDemo {
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    long startTime = System.currentTimeMillis();
    // 3个任务，目前开启多个异步任务来处理，请问耗时多少?   没有要get值的 54 毫秒
    //   要get 554 毫秒  ， 注意 如果线程中还没有处理完成，用get会堵塞后面的代码  所有之后获取放回值
    ExecutorService threadPool = Executors.newFixedThreadPool(3);
    FutureTask<String> f1 = new FutureTask<String>(() -> {
      TimeUnit.MILLISECONDS.sleep(500);
      return "task1 over";
    });
    threadPool.submit(f1);


    FutureTask<String> f2 = new FutureTask<String>(() -> {
      TimeUnit.MILLISECONDS.sleep(300);
      return "task2 over";
    });
    threadPool.submit(f2);


    FutureTask<String> f3 = new FutureTask<String>(() -> {
      TimeUnit.MILLISECONDS.sleep(300);
      return "task3 over";
    });
    threadPool.submit(f3);

    System.out.println(f1.get());
    System.out.println(f2.get());
    System.out.println(f3.get());

    long endTime = System.currentTimeMillis();
    System.out.println("----costTime: " + (endTime - startTime) + " 毫秒");

    threadPool.shutdown();
  }

  private static void m1() throws InterruptedException {
    // 3个任务，目前只有一个线程main来处理，请问耗时多少? // 1125 毫秒
    long startTime = System.currentTimeMillis();

    // 暂定时间
    TimeUnit.MILLISECONDS.sleep(500);
    TimeUnit.MILLISECONDS.sleep(300);
    TimeUnit.MILLISECONDS.sleep(300);


    long endTime = System.currentTimeMillis();
    System.out.println("----costTime: " + (endTime - startTime) + " 毫秒");

    System.out.println(Thread.currentThread().getName() + "\t ----------end");
  }
}
