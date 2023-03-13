package com.happy.juc.JUC高级篇.CompletableFuture.CompletableFuture案例;

import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

// 电商比较的案例
// 需求说明:
//      1. 同一款产品，同时搜索出同款产品在各大电商平台的售价
//      2. 同一款产品，同时搜索出本产品在同一个电商平台下，各个入驻卖家售价是多少
public class CompletableFutureMallDemo {
  static List<NetMall> list = Arrays.asList( // 个大电商平台
      new NetMall("jd"),
      new NetMall("dangdang"),
      new NetMall("tapbao"),
      new NetMall("pdd"),
      new NetMall("tmall")

  );

  // step by step 一家家搜查                  所有店家            书名
  public static List<String> getPrice(List<NetMall> list, String productName) {
    //  《mysql》 in taobao price is 90.42
    return list.stream().map(netMall ->
            String.format(productName + " in %s price is %.2f",  // %s 店家   %.2f  价格    %占位符
                netMall.getNetMallName(), netMall.calcPrice(productName)))  // 店家  价格  参数
        .collect(Collectors.toList()); // 构建成List
  }

  public static List<String> getPriceByCompletableFuture(List<NetMall> list, String productName) {
    return list.stream().map(netMall -> {
      return CompletableFuture.supplyAsync(() -> {    // CompletableFuture<String>
        return String.format(
            productName + " in %s price is %.2f",
            netMall.getNetMallName(),
            netMall.calcPrice(productName)); // String
      });
    }).collect(Collectors.toList())  // List<CompletableFuture<String>>
        .stream().map(s -> s.join())  // CompletableFuture<String>  .join()  值
        .collect(Collectors.toList());  // 结果打包到List<String>中
  }

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    System.out.println("--------- 无 --------");
    long startTime = System.currentTimeMillis();

    List<String> list1 = getPrice(list, "mysql");

    for (String element : list1) {
      System.out.println(element);
    }

    long endTime = System.currentTimeMillis();
    System.err.println("------costTime: " + (endTime - startTime) + " 毫秒");
    TimeUnit.MILLISECONDS.sleep(100);
    System.out.println("--------- Completable --------");

    long startTime2 = System.currentTimeMillis();

    List<String> list2 = getPriceByCompletableFuture(list, "mysql");

    for (String element : list2) {
      System.out.println(element);
    }

    long endTime2 = System.currentTimeMillis();
    System.err.println("------costTime: " + (endTime2 - startTime2) + " 毫秒");

  }
}

//@Data
//@ToString
//@NoArgsConstructor
//@AllArgsConstructor
//@Accessors(chain = true)  // 可以用一行的形式进行赋值
class NetMall {
  @Getter  // 提供对应的get方法
  private String netMallName;

  public NetMall(String netMallName) {
    this.netMallName = netMallName;
  }

  public double calcPrice(String ptoductName) {
    // 等待  1秒
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ThreadLocalRandom.current().nextDouble() * 2 + ptoductName.charAt(0); // 随机价格
  }
}


