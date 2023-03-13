package com.happy.juc.JUC高级篇.AQS;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author happy
 */
public class AQSDemo {
  public static void main(String[] args) throws InterruptedException {
    ReentrantLock lock = new ReentrantLock();

    lock.lock();
    try {

    } finally {
      lock.unlock();
    }

    new CountDownLatch(10);

    new Semaphore(2);
  }
}
