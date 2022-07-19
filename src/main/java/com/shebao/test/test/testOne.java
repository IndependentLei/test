package com.shebao.test.test;

import org.checkerframework.checker.units.qual.A;
import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.atomic.LongAdder;

public class testOne {
    // 非阻塞队列
    @Test
    public void one(){
        // 先进后出
        // 双端队列
        Deque<String> strs = new LinkedList<>();
        // 压入元素(添加)
//        strs.add()
//        strs.offer();
        strs.addLast("1");
        strs.addFirst("2");
        strs.addFirst("3");
//        strs.addLast("4");
//        System.out.println(strs);
        // 弹出元素(删除)：remove()、poll()
//        String remove = strs.remove();// 抛异常
        String poll = strs.poll(); // 不抛异常
        System.out.println(strs.element()); //获取第一个值，没有值抛异常
        System.out.println(strs.peek()); // 获取第一个值，没有值不抛异常，返回null
        System.out.println(strs);
    }
    // 阻塞队列
    @Test
    public void two(){
        //阻塞队列接口有BlockingQueue和BlockingDeque两个，其中后者是双向队列。
        Queue<String> strs = new LinkedBlockingQueue<>(2);
        strs.add("1");
        strs.add("1");
        strs.add("2");
    }

    @Test
    public void test3(){
        AtomicInteger atomicInteger = new AtomicInteger(5);
        atomicInteger.getAndUpdate((p)-> p = p+2);
        System.out.println(atomicInteger);
    }
    @Test
    public void test4(){
        LongAdder longAdder = new LongAdder();
        longAdder.increment(); // 多个计算单元一起计算，效率高
    }

    // CAS版本号
    static AtomicStampedReference<String> atomicStampedReference = new AtomicStampedReference<>("A",0);

}
