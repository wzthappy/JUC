package com.happy.juc.JUC高级篇.ReentrantReadWiteLock_StampedLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// 锁降级  写 -> 读                       读 -> 写 X
public class LockDownGradingDemo {
  public static void main(String[] args) {
    ReadWriteLock rwLock = new ReentrantReadWriteLock();

    Lock readLock = rwLock.readLock();
    Lock writeLock = rwLock.writeLock();

    // 正常 A B两个线程
    // A
//    writeLock.lock();
//    System.out.println("-----写入");
//    writeLock.unlock();

    // B
//    readLock.lock();
//    System.out.println("-----读取");
//    readLock.unlock();

    // 本例，only one 同一个线程

    // biz ....
    readLock.lock();
    System.out.println("-----读取");

    writeLock.lock();
    System.out.println("-----写入");

    writeLock.unlock();
    readLock.unlock();
  }
}
