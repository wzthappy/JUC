package com.happy.juc.JUC高级篇.Synchronized与锁升级.JIT编译器对锁的优化.锁销除;

/**
 * @author happy
 */
public class LockClearUpDemo {
  static Object objectLock = new Object();

  public void m1 () {
//    synchronized (objectLock) {
//      System.out.println("------hello LockClearUpDemo");
//    }
    Object o = new Object();  // 每次调用都会创建新的锁(new)
    synchronized (o) {  // 锁销除: 无意义的锁，JIT编译器会无视他
      System.out.println("------hello LockClearUpDemo" + "\t" + o.hashCode()
          + "\t" + objectLock.hashCode());
    }
  }

  public static void main(String[] args) {
    LockClearUpDemo lockClearUpDemo = new LockClearUpDemo();

    for (int i = 1; i <= 10; i++) {
      new Thread(() -> {
        lockClearUpDemo.m1();
      }, String.valueOf(i)).start();
    }
  }
}
