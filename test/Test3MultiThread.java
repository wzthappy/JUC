package com.happy.juc.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 演示多个线程并发交替执行
 */
@Slf4j(topic = "c.test3MultiThread")
public class Test3MultiThread {
  public static void main(String[] args) {
    new Thread(() -> {
      while (true) {
        log.debug("running");
      }
    }, "t1").start();

    new Thread(() -> {
      while (true) {
        log.debug("running");
      }
    }, "t2").start();
  }
}
