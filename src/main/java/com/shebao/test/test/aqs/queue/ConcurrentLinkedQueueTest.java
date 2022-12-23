package com.shebao.test.test.aqs.queue;

public class ConcurrentLinkedQueueTest {
    public static void main(String[] args) {
        /**
         * 10.ConcurrentLinkedQueue
         * ConcurrentLinkedQueue的设计与LinkedBlockingQueue非常像，也是
         * ·两把【锁】，同一时刻，可以允许两个线程同时（一个生产者与一个消费者）执行
         * ·dummy节点的引入让两把【锁】将来锁住的是不同对象，避免竞争
         * ·只是这【锁】使用了cas来实现
         * 事实上，ConcurrentLinkedQueue应用还是非常广泛的
         * 例如之前讲的Tomcat的Connector结构时，Acceptor作为生产者向Poller消费者传递事件信息时，正是采用
         * 了ConcurrentLinkedQueue将SocketChannel给Poller使用
         */
    }
}

