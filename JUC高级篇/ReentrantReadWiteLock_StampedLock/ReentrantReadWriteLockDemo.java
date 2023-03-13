package com.happy.juc.JUC高级篇.ReentrantReadWiteLock_StampedLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

class MyResource { // 资源类，模拟一个简单的缓存
  Map<String, String> map = new HashMap<>();
  // 等价于synchronized
  Lock lock = new ReentrantLock();
  // 读写互斥，读读共享
  ReadWriteLock rwLock  = new ReentrantReadWriteLock();

  public void write(String key, String value) {
    rwLock.writeLock().lock();
//    lock.lock();
    try {
      System.out.println(Thread.currentThread().getName() + "\t" + "正在写入");
      map.put(key, value);
      // 暂停时间
      TimeUnit.MILLISECONDS.sleep(500);
      System.out.println(Thread.currentThread().getName() + "\t" + "完成写入");
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
//      lock.unlock();
      rwLock.writeLock().unlock();
    }
  }

  public void read (String key) {
//    lock.lock();
    rwLock.readLock().lock();
    try {
      System.out.println(Thread.currentThread().getName() + "\t" + "正在读取");
      String result = map.get(key);
      // 暂停毫秒
//      TimeUnit.MILLISECONDS.sleep(200);
      // 暂停2000毫秒，演示读锁没有完成之前，写锁无法获得
      TimeUnit.MILLISECONDS.sleep(2000);
      System.out.println(Thread.currentThread().getName() + "\t" + "完成读取" + "\t" + result);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
//      lock.unlock();
      rwLock.readLock().unlock();
    }
  }
}

/**
 * @author happy
 */
public class ReentrantReadWriteLockDemo {
  public static void main(String[] args) throws InterruptedException {
    MyResource myResource = new MyResource();
    for (int i = 1; i <= 10; i++) {
      int finalI = i;
      new Thread(() -> {
        myResource.write(finalI + "", finalI + "");
      }, String.valueOf(i)).start();
    }

    for (int i = 1; i <= 10; i++) {
      int finalI = i;
      new Thread(() -> {
        myResource.read(finalI + "");
      }, String.valueOf(i)).start();
    }

    TimeUnit.SECONDS.sleep(1);

    for (int i = 1; i <= 3; i++) {
      int finalI = i;
      new Thread(() -> {
        myResource.write(finalI + "", finalI + "");
      }, "新写锁线程->" + String.valueOf(i)).start();
    }
  }
}
