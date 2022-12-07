package com.shebao.test.test.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(1);
//        int i = atomicInteger.updateAndGet(x -> x * 11); // 任意的复杂计算
//        System.out.println(i);
        updateAndGet(atomicInteger, prev -> prev * 10);
        System.out.println(atomicInteger.get());
    }


    public static void updateAndGet(AtomicInteger i,OptionDetail optionDetail){
        while (true){
            int prev = i.get();
            int next = optionDetail.apply(prev); // 将这个回调函数传进来，具体的操作，让程序员自己实现
            if(i.compareAndSet(prev,next)){
                break;
            }
        }
    }
}

@FunctionalInterface
interface OptionDetail{
    int apply(int prev);
}
