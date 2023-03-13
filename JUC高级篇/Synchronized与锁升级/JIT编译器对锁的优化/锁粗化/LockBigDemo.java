package com.happy.juc.JUC高级篇.Synchronized与锁升级.JIT编译器对锁的优化.锁粗化;

public class LockBigDemo {
  static Object objectLock = new Object();

  public static void main(String[] args) {
    new Thread(() -> {
      synchronized (objectLock) {
        System.out.println("11111111");
      }
      synchronized (objectLock) {
        System.out.println("22222222");
      }
      synchronized (objectLock) {
        System.out.println("33333333");
      }
      synchronized (objectLock) {
        System.out.println("44444444");
      }
      // ========>  等价与
      synchronized (objectLock) {
        System.out.println("11111111");
        System.out.println("22222222");
        System.out.println("33333333");
        System.out.println("44444444");
      }
    }, "t1").start();
  }
}
