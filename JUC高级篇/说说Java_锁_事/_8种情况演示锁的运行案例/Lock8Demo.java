package com.happy.juc.JUC高级篇.说说Java_锁_事._8种情况演示锁的运行案例;

import java.util.concurrent.TimeUnit;

class Phone { // 资源类
  public static synchronized void sendEmail() {
    try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {throw new RuntimeException(e);}
    System.out.println("-----sendEmail");
  }

  public synchronized void sendSMS() {
    System.out.println("----sendSMS");
  }

  public void hello() {
    System.out.println("-----hello");
  }
}


// 题目: 谈谈你对多线程的理解，8锁案例说明 ..
public class Lock8Demo {
  public static void main(String[] args) throws InterruptedException {
    Phone phone1 = new Phone();
    Phone phone2 = new Phone();

    new Thread(() -> {
      phone1.sendEmail();
    }, "a").start();

    TimeUnit.MILLISECONDS.sleep(200);

    new Thread(() -> {
//      phone1.sendSMS();
      phone2.sendSMS();
    }, "b").start();

  }
}
