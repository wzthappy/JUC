package com.happy.juc.JUC高级篇.CAS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 题目: 实现一个自旋锁,复习CAS思想
 *  自旋锁好处: 循环比较获取没有类似wait的堵塞.
 *      缺点: 循环会损耗CPU
 *
 *  通过CAS操作完成自旋锁,A线程先进去调用myLock方法自己持有锁5秒钟,B随后进来后发现
 *    当前有线程持有锁,所以只能通过自旋锁等待,直到A释放锁B后抢到.
 */
public class SpinLockDemo {
  AtomicReference<Thread> atomicReference = new AtomicReference<>();

  public void lock () {
    Thread thread = Thread.currentThread();
    System.out.println(Thread.currentThread().getName() + "\t" + "----come in");
    while (!atomicReference.compareAndSet(null, thread)) {

    }
  }

  public void unLock () {
    Thread thread = Thread.currentThread();
    atomicReference.compareAndSet(thread, null);
    System.out.println(Thread.currentThread().getName() + "\t" + "----task over,unLock...");
  }

  public static void main(String[] args) throws InterruptedException {
    SpinLockDemo spinLockDemo = new SpinLockDemo();

    new Thread(() -> {
      spinLockDemo.lock();
      // 暂停几秒钟线程
      try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {throw new RuntimeException(e);}
      spinLockDemo.unLock();
    }, "A").start();

    // 暂停500毫秒,线程A先于B启动
    TimeUnit.MILLISECONDS.sleep(500);

    new Thread(() -> {
      spinLockDemo.lock();

      spinLockDemo.unLock();
    }, "B").start();
  }
}
