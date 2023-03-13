package com.happy.juc.JUC高级篇.原子操作类之18罗汉增强.基本类型原子类;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyNumber {
  AtomicInteger atomicInteger = new AtomicInteger();

  public void addPlusPlus() {
    atomicInteger.getAndIncrement(); // +1
  }
}

public class AtomicIntegerDemo {
  public static final int SIZE = 50;

  public static void main(String[] args) throws InterruptedException {
    MyNumber myNumber = new MyNumber();
    CountDownLatch countDownLatch = new CountDownLatch(SIZE);

    for (int i = 1; i <= SIZE; i++) {
      new Thread(() -> {
        try {
          for (int i1 = 1; i1 <= 1000; i1++) {
            myNumber.addPlusPlus();
          }
        } finally {
          countDownLatch.countDown();
        }
      }).start();
    }

    // 等待上面50个线程全部计算完成后,在取获取最终值
//    TimeUnit.SECONDS.sleep(2);

    countDownLatch.await();

    System.out.println(Thread.currentThread().getName() + "\t" +
        "result: " + myNumber.atomicInteger.get());
  }
}
