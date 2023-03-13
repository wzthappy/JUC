package com.happy.juc.JUC高级篇.CAS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

@Getter
@ToString
@AllArgsConstructor
class User {
  String userName;
  int age;
}

public class AtomicReferenceDemo {
  public static void main(String[] args) {
    AtomicReference<User> atomicReference = new AtomicReference<>();

    User z3 = new User("zs", 22);
    User li4 = new User("li4", 28);

    atomicReference.set(z3);
    System.out.println(atomicReference.compareAndSet(z3, li4) + "\t" +
        atomicReference.get().toString());

    System.out.println(atomicReference.compareAndSet(z3, li4) + "\t" +
        atomicReference.get().toString());
  }
}
