package com.happy.juc.JUC基础篇.ReentrantReadWriteLock读写锁.锁降级;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// 演示读写锁降级
public class Demo1 {
  public static void main(String[] args) {
    // 创建可重入读写锁对象
    ReadWriteLock rwLock = new ReentrantReadWriteLock();
    Lock rlock = rwLock.readLock(); // 读锁
    Lock wlock = rwLock.writeLock(); // 写锁

    // 锁降级
    // 1. 获取到写锁
    wlock.lock();
    System.out.println("atguigu");

    // 2. 获取到写锁
    rlock.lock();
    System.out.println("------read");

    // 3. 释放写锁
    wlock.unlock();
    // 4. 释放读锁
    rlock.unlock();



  }
}
