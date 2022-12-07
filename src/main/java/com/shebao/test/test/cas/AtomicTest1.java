package com.shebao.test.test.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicTest1 {
//    private static  AtomicReference<String> ref = new AtomicReference<>("A");

    // 解决ABA问题，加一个版本号，每次cas操作成功，这个版本号加一
    private static AtomicStampedReference<String> ref = new AtomicStampedReference<>("A",0);
    public static void main(String[] args) {

        /// ABA 问题
        String prev = ref.getReference();

        int stamp = ref.getStamp();
        other();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("-----> result:"+ref.compareAndSet(prev,"C",stamp,stamp+1));
        System.out.println("-----> stamp:"+ref.getStamp());
    }

    public static void other(){
        int stamp = ref.getStamp();
        new Thread(()->{
            System.out.println("------->t1:"+ref.compareAndSet(ref.getReference(),"B",stamp,stamp+1));
        },"t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int stamp1 = ref.getStamp();
        new Thread(()->{
            System.out.println("------->t2:"+ref.compareAndSet(ref.getReference(),"A",stamp1,stamp1+1));
        },"t2").start();
    }
}
