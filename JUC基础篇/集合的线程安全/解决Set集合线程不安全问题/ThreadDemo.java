package com.happy.juc.JUC基础篇.集合的线程安全.解决Set集合线程不安全问题;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

public class ThreadDemo {
  public static void main(String[] args) {
    // Set<String> set = new HashSet<>();
    CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();

    for (int i = 0; i < 30; i++) {
      new Thread(() -> {
        // 向集合添加内容
        set.add(UUID.randomUUID().toString().substring(0, 8));
        // 从集合获取内容
        System.out.println(set);
      }, String.valueOf(i)).start();
    }
  }
}
