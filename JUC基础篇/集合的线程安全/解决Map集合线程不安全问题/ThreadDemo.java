package com.happy.juc.JUC基础篇.集合的线程安全.解决Map集合线程不安全问题;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadDemo {
  public static void main(String[] args) {
//    HashMap<String, String> map = new HashMap<>();
    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

    for (int i = 0; i < 30; i++) {
      String key = String.valueOf(i);
      new Thread(() -> {
        // 向集合添加内容
        map.put(key, UUID.randomUUID().toString().substring(0, 8));
        // 从集合获取内容
        System.out.println(map);
      }, String.valueOf(i)).start();
    }
  }
}
