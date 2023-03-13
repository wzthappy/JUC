package com.happy.juc.JUC基础篇.ReentrantReadWriteLock读写锁.模拟缓存的过程;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// 创建资源类
class MyCache {
  // 创建一个map集合   volatile 表示数据会不断的发生变化
  private volatile Map<String, Object> map = new HashMap<>();

  // 创建读写锁对象
  private ReadWriteLock rwLock = new ReentrantReadWriteLock();

  // 放数据
  public void put (String key, Object value) {
    // 添加写锁
    rwLock.writeLock().lock();
    
    try {
      System.out.println(Thread.currentThread().getName() + " 正在写操作" + key);
      // 暂停一会
      TimeUnit.MICROSECONDS.sleep(300);
      // 放数据
      map.put(key, value);
      System.out.println(Thread.currentThread().getName() + " 写完了" + key);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      // 释放写锁
      rwLock.writeLock().unlock();
    }

  }

  // 取数据
  public Object get (String key) {
    // 添加读锁
    rwLock.readLock().lock();

    try {
      Object result = null;
      System.out.println(Thread.currentThread().getName() + " 正在读取操作" + key);
      // 暂停一会
      TimeUnit.MICROSECONDS.sleep(300);
      // 取数据
      result = map.get(key);

      System.out.println(Thread.currentThread().getName() + " 取完了" + key);
      return result;
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      // 释放读锁
      rwLock.readLock().unlock();
    }

  }
}

public class ReadWriteLockDemo {
  public static void main(String[] args) {
    MyCache myCache = new MyCache();
    // 创建线程放数据
    for (int i = 1; i <= 5; i++) {
      final int num = i;
      new Thread(() -> {
        myCache.put(num + "", num + "");
      }, String.valueOf(i)).start();
    }

    // 读 创建线程放数据
    for (int i = 1; i <= 5; i++) {
      final int num = i;
      new Thread(() -> {
        System.out.println("读到的数据 -> " + myCache.get(num + ""));
      }, String.valueOf(i)).start();
    }
  }
}
