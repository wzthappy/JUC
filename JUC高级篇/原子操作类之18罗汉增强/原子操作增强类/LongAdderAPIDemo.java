package com.happy.juc.JUC高级篇.原子操作类之18罗汉增强.原子操作增强类;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author happy
 */
public class LongAdderAPIDemo {
  public static void main(String[] args) {
    LongAdder longAdder = new LongAdder();
    longAdder.increment();
    longAdder.increment();
    longAdder.increment();

    System.out.println(longAdder.sum());

    LongAccumulator accumulator = new LongAccumulator((x, y) -> {
      System.out.println("=========");
      System.out.println(y);
      return x + y;
    }, 0);
    accumulator.accumulate(1); // 1
    accumulator.accumulate(3); // 4

    System.out.println(accumulator.get());
  }
}
