package com.happy.juc.JUC基础篇.集合的线程安全.解决List集合线程不安全问题;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * list集合线程不安全
 */
public class ThreadDemo4 {
  public static void main(String[] args) {
    // 创建ArrayList集合
//    List<String> list = new ArrayList<>();  // 他底层没有加上锁，所以是线程不安全的
//    List<String> list = new Vector<>();  // 1. 他底层加上锁，所以是线程安全的，但效率太低
//    List<String> list =
//        Collections.synchronizedList(new ArrayList<>()); // 2. 他底层加上锁，所以是线程安全的，但效率太低

    List<String> list = new CopyOnWriteArrayList<>(); // lock锁 + 写时复制技术

    for (int i = 0; i < 10; i++) {
      new Thread(() -> {
        // 向集合里面添加内容
        list.add(UUID.randomUUID().toString().substring(0, 8));

        // 从集合中获取内容
        System.out.println(list);

      }, String.valueOf(i)).start();
    }
  }
}
