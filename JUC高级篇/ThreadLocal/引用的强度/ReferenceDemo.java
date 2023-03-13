package com.happy.juc.JUC高级篇.ThreadLocal.引用的强度;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

class MyObject{
  // 这个方法一般不用复写，只是为了教学说明
  @Override
  protected void finalize() throws Throwable {
    // finalize的通常目的是在对象被不可撤销地丢弃之前执行清理操作
    System.out.println("-----invoke finalize method...");
  }
}

/**
 * @author happy
 */
public class ReferenceDemo {
  public static void main(String[] args) {
    MyObject myObject = new MyObject();
    // 引用队列
    ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();
    // 虚引用
    PhantomReference<MyObject> phantomReference = new PhantomReference<>(myObject, referenceQueue);

//    System.out.println(phantomReference.get());  // null
    List<byte[]> list = new ArrayList<>();
    Thread t1 = new Thread(() -> {
      while (true) {
        list.add(new byte[1024 * 1024]);
//        try {TimeUnit.MILLISECONDS.sleep(500);} catch (InterruptedException e) {throw new RuntimeException(e);}
        System.out.println(phantomReference.get() + "\t" + "list add ok");
      }
    }, "t1");
    t1.start();

    new Thread(() -> {
      while (true) {
        Reference<? extends MyObject> reference = referenceQueue.poll();
        if (reference != null) {
          System.out.println("--------有虚对象回收加入了队列");
          System.exit(0);
//          break;
        }
      }
    }, "t2").start();
  }

  private static void weakReference() {
    // 弱引用
    WeakReference<MyObject> weakReference = new WeakReference<>(new MyObject());
    System.out.println("-----gc before 内存够用: " + weakReference.get());
    System.gc();
    // 暂停几秒钟线程
    try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}

    System.out.println("-----gc after 内存够用: " + weakReference.get());
  }

  private static void softReference() {
    // 软引用
    SoftReference<MyObject> softReference = new SoftReference<>(new MyObject());
//    System.out.println("------- softReference: " + softReference);
    System.gc();
    try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}

    System.out.println("----- gc after内存够用: " + softReference.get());
    try {
      byte[] bytes = new byte[2000 * 1024 * 1024]; //2000MB对象
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      System.out.println("----- gc after内存不够用: " + softReference.get());
    }
  }

  private static void strongReference() {
    // 默认_强引用
    MyObject myObject = new MyObject();
    System.out.println("gc before: " + myObject);

    myObject = null;
    System.gc(); // 人工调用gc，一般不用

    System.out.println("gc after: " + myObject);
  }
}
