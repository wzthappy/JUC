package com.happy.juc.JUC高级篇.ReentrantReadWiteLock_StampedLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * @author happy
 * <p>
 * stampedLock = ReentrantReadWriteLock + 读的过程中也允许获取写锁的介入
 */
public class StampedLockDemo {
  static int number = 37;
  static StampedLock stampedLock = new StampedLock();

  public void write() {
    long stamp = stampedLock.writeLock();
    System.out.println(Thread.currentThread().getName() + "\t" + "写线程准备修改");
    try {
      number = number + 13;
    } finally {
      stampedLock.unlockWrite(stamp);
    }
    System.out.println(Thread.currentThread().getName() + "\t" + " 写线程结束修改");
  }

  // 悲观锁，读没有完成时候写锁无法获取锁
  public void read() {
    long stamp = stampedLock.readLock();
    System.out.println(Thread.currentThread().getName() + "\t" + " come in readLock code block，" +
        "4 seconds continue...");
    try {
      for (int i = 0; i < 4; i++) {
        // 暂停几秒
        TimeUnit.SECONDS.sleep(1);
        System.out.println(Thread.currentThread().getName() + "\t" + " 正在读取中.......");
      }

      int result = number;
      System.out.println(Thread.currentThread().getName() + "\t" + " 获得成员变量值result: " + result);
      System.out.println("写线程没有修改成功，读锁时候写锁无法介入，传统的读写互斥");
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      stampedLock.unlockRead(stamp);
    }
  }

  // 乐观读，读的过程中也允许写的介入
  public void tryOptimisticRead() {
    long stamp = stampedLock.tryOptimisticRead();
    int result = number;
    // 故意间隔4秒钟，很乐观认为读取中没有其他线程修改过number值，具体靠判断
    System.out.println("4秒前stampedLock，validate方法值(true无修改，false有修改)" + "\t"
        + stampedLock.validate(stamp));
    try {
      for (int i = 0; i < 4; i++) {
        TimeUnit.SECONDS.sleep(1);
        System.out.println(Thread.currentThread().getName() + "\t" + "正在读取... " + i + " 秒后" +
            "stampedLock，validate方法值(true无修改，false有修改)" + "\t"
            + stampedLock.validate(stamp));
      }

      if (!stampedLock.validate(stamp)) {
        System.out.println("有人修改过------有写操作");
        stamp = stampedLock.readLock();
        try {
          System.out.println("从乐观读 升级为 悲观读");
          result = number;
          System.out.println("查询悲观读后result: " + result);
        } finally {
          stampedLock.unlockRead(stamp);
        }
      }
      System.out.println(Thread.currentThread().getName() + "\t" + "finally value: " + result);

    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) throws InterruptedException {
    StampedLockDemo resource = new StampedLockDemo();
/*  new Thread(() -> {
      resource.read();
    }, "readThread").start();
    TimeUnit.SECONDS.sleep(1);

    new Thread(() -> {
      System.out.println(Thread.currentThread().getName() + "\t" + "come in");
      resource.write();
      System.out.println("--------------------");
    }, "writeThread").start();

    TimeUnit.SECONDS.sleep(4);
    System.out.println(Thread.currentThread().getName() + "\t" + "number: " + number);*/

    new Thread(() -> {
      resource.tryOptimisticRead();
    }, "readThread").start();
    // 暂停2秒钟线程，读过程可以写介入，演示
    TimeUnit.SECONDS.sleep(2);
//    TimeUnit.SECONDS.sleep(6);

    new Thread(() -> {
      resource.write();
    }, "writeThread").start();
  }
}
