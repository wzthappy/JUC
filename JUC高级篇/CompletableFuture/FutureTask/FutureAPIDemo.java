package com.happy.juc.JUC高级篇.CompletableFuture.FutureTask;

import java.util.concurrent.*;

public class FutureAPIDemo {
  public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1); // 线程池
    FutureTask<String> futureTask = new FutureTask<>(() -> {
      Thread.currentThread().setName("张三");
      System.out.println(Thread.currentThread().getName() + "\t -------come in");
      // 暂定几秒钟线程
      TimeUnit.SECONDS.sleep(5);
      return "task over";
    });
    fixedThreadPool.submit(futureTask); // 启动

    System.out.println(Thread.currentThread().getName() + "\t ----忙其它任务了");

//    System.out.println(futureTask.get(3, TimeUnit.SECONDS)); // 获取放回值， 没有获取到值  会堵塞之后的代码

    while (true) {
      if (futureTask.isDone()) { // 判断是否完成
        System.out.println(futureTask.get());
        break;
      } else {
        // 暂停毫秒
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println("正在处理中....");
      }
    }

    fixedThreadPool.shutdown(); // 关闭线程池
  }
}
