package com.happy.juc.JUC基础篇.多线程锁.可重入锁;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 可重入锁
public class SyncLockDemo {

  public synchronized void add () {
    add();   // 证明synchronized是可重入锁
  }



  public static void main(String[] args) {
    // lock 演示可重入锁
    Lock lock = new ReentrantLock();

    new Thread(() -> {
      try {
        // 上锁
        lock.lock();
        System.out.println(Thread.currentThread().getName() + " 外层");

        try {
          lock.lock();
          System.out.println(Thread.currentThread().getName() + " 内层");
        } finally {
//           lock.unlock();
        }

      } finally {
        lock.unlock();
      }
    }, "t1").start();
    new Thread(() -> {
      lock.lock();
      try {
        System.out.println("aaaa");
      } finally {
        lock.unlock();
      }
    }, "aa").start();

//    new SyncLockDemo().add();
    // synchronized
//    Object o = new Object();
//    new Thread(() -> {
//      synchronized (o) {
//        System.out.println(Thread.currentThread().getName() + " 外层");
//
//        synchronized (o) {
//          System.out.println(Thread.currentThread().getName() + " 中层");
//
//          synchronized (o) {
//            System.out.println(Thread.currentThread().getName() + " 内层");
//          }
//
//        }
//
//      }
//    }, "t1").start();
  }
}
