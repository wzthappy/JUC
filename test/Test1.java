package com.happy.juc.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test1")
public class Test1 {
  public static void test1() {
    Thread t = new Thread() {
      @Override
      public void run() {
        log.debug("running");
      }
    };
    t.setName("t1");
    t.start();

    log.debug("running");
  }

  public static void test2() {
//    Runnable runnable = () -> {
//      log.debug("running");
//    };
    Thread t = new Thread(() -> log.debug("running"), "t2");
    t.start();

    log.debug("running");
  }
}
