package com.happy.juc.JUC高级篇.ThreadLocal;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

class House { // 资源类
  int saleCount = 0;

  public synchronized void saleHouse() {
    saleCount++;
  }

//  ThreadLocal<Integer> saleVolume = new ThreadLocal<Integer>() {
//    @Override
//    protected Integer initialValue() {
//      return 0;
//    }
//  };

  ThreadLocal<Integer> saleVolume =
      ThreadLocal.withInitial(() -> 0); // 里面的值是每个线程独有的

  public void saleVolumeByThreadLocal () {
    saleVolume.set(1 + saleVolume.get()); // +1
  }
}

/**
 * @author happy
 * 需求1: 5个销售卖房子，集团高层只关心销售总量的准确统计数
 *
 * 需求2: 5个销售卖完随机数房子，各自独立销售额度，自己业绩按提成走，分灶吃饭，各个销售自己动手，丰衣足食
 *
 */
public class ThreadLocalDemo {
  public static void main(String[] args) throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(5);
    House house = new House();

    for (int i = 1; i <= 5; i++) {
      new Thread(() -> {
        try {
          int size = new Random().nextInt(5) + 1;
          for (int i1 = 1; i1 <= size; i1++) {
            house.saleHouse();
            house.saleVolumeByThreadLocal();
          }
          System.out.println(Thread.currentThread().getName() + "\t" +
              "号销售卖出: " + house.saleVolume.get());
        } finally {
          countDownLatch.countDown();
          house.saleVolume.remove();
        }
      }, String.valueOf(i)).start();
    }

    countDownLatch.await();
    System.out.println(Thread.currentThread().getName() + "\t" + "共计卖出多少套: "
        + house.saleCount);
  }
}
