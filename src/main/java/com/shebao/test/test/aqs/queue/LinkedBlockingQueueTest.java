package com.shebao.test.test.aqs.queue;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueTest {
    public static void main(String[] args) {
        /**
         * 3.性能比较
         * 主要列举LinkedBlockingQueue与ArrayBlockingQueue的性能比较
         * ·Linked支持有界，Aray强制有界
         * ·Linked实现是链表，Array实现是数组
         * ·Linked是懒惰的，而Aray需要提前初始化Node数组
         * ·Linked每次入队会生成新Node,而Array的Node是提前创建好的
         * ·Linked两把锁，Array一把锁
         */
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("1");
    }
}
