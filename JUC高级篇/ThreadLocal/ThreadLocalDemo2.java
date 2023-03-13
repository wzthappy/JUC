package com.happy.juc.JUC高级篇.ThreadLocal;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MyData {
  ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

  public void add () {
    threadLocal.set(1 + threadLocal.get());
  }
}

/**
 * @author happy
 * 【强制】必须回收自定义的 ThreadLocal 变量，尤其在线程池场景下，线程经常会被复用，如果不清理
 *   自定义的 ThreadLocal 变量，可能会影响后续业务逻辑和造成内存泄漏等问题。尽快在代理中使用
 *   try-finally 块进行回收。
 */
public class ThreadLocalDemo2 {
  public static void main(String[] args) {
    MyData myData = new MyData();
    ExecutorService threadPool = Executors.newFixedThreadPool(3);

    try {
      for (int i = 0; i < 10; i++) {
        CompletableFuture.runAsync(() -> {
          try {
            Integer beforeInt = myData.threadLocal.get();
            myData.add();
            Integer afterInt = myData.threadLocal.get();
            System.out.println(Thread.currentThread().getName() + "---- \t" +
                "beforeInt: " + beforeInt + "\tafterInt: " + afterInt);
          } finally {
            myData.threadLocal.remove();  // 用完 清理自定义的 ThreadLocal 变量
          }
        }, threadPool);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      threadPool.shutdown();
    }
  }
}
