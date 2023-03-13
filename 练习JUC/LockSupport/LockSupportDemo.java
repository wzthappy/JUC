package com.happy.juc.练习JUC.LockSupport;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {
  public static void main(String[] args) {
    Thread a = new Thread(() -> {
      try {TimeUnit.SECONDS.sleep(3L);} catch (InterruptedException e) {throw new RuntimeException(e);}
      System.out.println(Thread.currentThread().getName() + "\t" + "------come in " + System.currentTimeMillis());
      System.out.println(new Date().getTime());
      LockSupport.park(); // 堵塞... 等待通知唤醒，他需要通过许可证
      System.out.println(Thread.currentThread().getName() + "\t" + "------被唤醒" + System.currentTimeMillis());
    }, "a");
    a.start();

    new Thread(() -> {
      LockSupport.unpark(a);
      System.out.println(Thread.currentThread().getName() + "\t" + "------通知了");
    }, "b").start();
  }
}
