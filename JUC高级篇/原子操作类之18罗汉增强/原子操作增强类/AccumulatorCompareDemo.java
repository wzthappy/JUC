package com.happy.juc.JUC高级篇.原子操作类之18罗汉增强.原子操作增强类;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

class ClickNumber { // 资源类
  long number = 0;
  public synchronized void clickBySynchronized() {
    number++;
  }

  AtomicLong atomicLong = new AtomicLong(0);

  public void clickByAtomicLong () {
    atomicLong.getAndIncrement(); // +1
  }

  LongAdder longAdder = new LongAdder();
  public void clickByLongAdder() {
    longAdder.increment(); // +1
  }

  LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);
  public void clickByLongAccumulator () {
    longAccumulator.accumulate(1);
  }
}

/**
 * @author happy
 * 需求: 50个线程，每个线程100w次，总点赞数出来
 */
public class AccumulatorCompareDemo {
  public static final int _1W = 10000;
  public static final int threadNumber = 50;

  public static void main(String[] args) throws InterruptedException {
    String[] arr = {"synchronized", "atomicLong", "longAdder", "longAccumulator"};
    CountDownLatch countDownLatch1 = new CountDownLatch(threadNumber);
    CountDownLatch countDownLatch2 = new CountDownLatch(threadNumber);
    CountDownLatch countDownLatch3 = new CountDownLatch(threadNumber);
    CountDownLatch countDownLatch4 = new CountDownLatch(threadNumber);
    CountDownLatch[] countDownLatches = {countDownLatch1, countDownLatch2,
        countDownLatch3, countDownLatch4};
    ClickNumber clickNumber = new ClickNumber();
    long startTime;
    long endTime;

    for (int x = 0; x < arr.length; x++) {
      startTime = System.currentTimeMillis();
      for (int i = 1; i <= threadNumber; i++) {
        int finalX = x;
        new Thread(() -> {
          try {
            if ("synchronized".equals(arr[finalX])) {
              for (int i1 = 1; i1 <= 100 * _1W; i1++) {  // synchronized
                clickNumber.clickBySynchronized();
              }
            } else if("atomicLong".equals(arr[finalX])) { // atomicLong
              for (int i1 = 1; i1 <= 100 * _1W; i1++) {
                clickNumber.clickByAtomicLong();
              }
            } else if ("longAdder".equals(arr[finalX])) { // longAdder
              for (int i1 = 1; i1 <= 100 * _1W; i1++) {
                clickNumber.clickByLongAdder();
              }
            } else {
              for (int i1 = 1; i1 <= 100 * _1W; i1++) {  // longAccumulator
                clickNumber.clickByLongAccumulator();
              }
            }
          } finally {
            countDownLatches[finalX].countDown();
          }
        }, String.valueOf(i)).start();
      }
      countDownLatches[x].await();
      endTime = System.currentTimeMillis();

      System.out.println(arr[x] + "---- costTime: " + (endTime - startTime) + " 毫秒" + "\t--------"
          + arr[x] + ": " + number(arr[x], clickNumber));
    }
  }

  private static Long number (String arr, ClickNumber clickNumber) {
    if ("synchronized".equals(arr)) {  // synchronized
      return clickNumber.number;
    } else if("atomicLong".equals(arr)) { // atomicLong
      return clickNumber.atomicLong.get();
    } else if ("longAdder".equals(arr)) { // longAdder
      return clickNumber.longAdder.sum();
    } else {  // longAccumulator
      return clickNumber.longAccumulator.get();
    }
  }
}
