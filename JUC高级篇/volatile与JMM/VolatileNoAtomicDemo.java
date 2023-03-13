package com.happy.juc.JUC高级篇.volatile与JMM;

import java.util.concurrent.TimeUnit;

class MyNumber {
  volatile int number;
  public void addplus() {
    number++;
  }
}

public class VolatileNoAtomicDemo {
  public static void main(String[] args) throws InterruptedException {
    MyNumber myNumber = new MyNumber();

    for (int i = 0; i < 10; i++) {
      new Thread(() -> {
        for (int j = 1; j <= 1000; j++) {
          myNumber.addplus();
        }
      }, String.valueOf(i)).start();
    }

    TimeUnit.SECONDS.sleep(2);

    System.out.println(myNumber.number);
  }
}

