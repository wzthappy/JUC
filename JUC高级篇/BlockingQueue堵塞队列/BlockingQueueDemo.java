package com.happy.juc.JUC高级篇.BlockingQueue堵塞队列;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {
  public static void main(String[] args) throws InterruptedException {
    // 创建一个堵塞队列
    BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

    // 异常方法
    // 添加数据
//    System.out.println(blockingQueue.add("a"));
//    System.out.println(blockingQueue.add("b"));
//    System.out.println(blockingQueue.add("c"));
//    System.out.println(blockingQueue.element());
//    System.out.println(blockingQueue.add("w")); // IllegalStateException
    // 取数据
//    System.out.println(blockingQueue.remove());
//    System.out.println(blockingQueue.remove());
//    System.out.println(blockingQueue.remove());
//    System.out.println(blockingQueue.remove()); // .NoSuchElementExceptio


    // 特殊方法
    // 添加元素
//    System.out.println(blockingQueue.offer("a"));
//    System.out.println(blockingQueue.offer("b"));
//    System.out.println(blockingQueue.offer("c"));
//    System.out.println(blockingQueue.offer("www")); // false 满了
//    // 移除
//    System.out.println(blockingQueue.poll());
//    System.out.println(blockingQueue.poll());
//    System.out.println(blockingQueue.poll());
//    System.out.println(blockingQueue.poll());  // null


    // 堵塞方法
    // 添加元素
//    blockingQueue.put("a");
//    blockingQueue.put("b");
//    blockingQueue.put("c");
//    blockingQueue.put("www"); // 堵塞了

//    System.out.println(blockingQueue.take());
//    System.out.println(blockingQueue.take());
//    System.out.println(blockingQueue.take());
//    System.out.println(blockingQueue.take()); // 堵塞了

    // 超时方法
    // 添加元素
    System.out.println(blockingQueue.offer("a"));
    System.out.println(blockingQueue.offer("b"));
    System.out.println(blockingQueue.offer("c"));
    System.out.println(blockingQueue.offer("www", 3L, TimeUnit.SECONDS));
  }
}
