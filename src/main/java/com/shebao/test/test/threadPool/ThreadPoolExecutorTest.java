package com.shebao.test.test.threadPool;

import lombok.extern.slf4j.Slf4j;
import org.testng.internal.thread.DefaultThreadPoolExecutorFactory;

import java.util.ArrayDeque;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ThreadPoolExecutorTest {
//    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,10,1000, TimeUnit.MINUTES,new ArrayDeque<>(), new DefaultThreadPoolExecutorFactory());
    /**
     *     public ThreadPoolExecutor(int corePoolSize,  核心线程数目（最多保留的线程数）
     *                               int maximumPoolSize, 最大线程数目   最大线程数目 - 核心线程数目 = 救急线程数目
     *                               long keepAliveTime,  生存时间 - 针对救急线程
     *                               TimeUnit unit,  时间单位 - 针对救急线程
     *                               BlockingQueue<Runnable> workQueue,阻塞队列
     *                               ThreadFactory threadFactory,  线程工程 - 可以为线程创建的时候起个好名字
     *                               RejectedExecutionHandler handler) 拒绝策略
     */

    /**
     * ·线程池中刚开始没有线程，当一个任务提交给线程池后，线程池会创建一个新线程来执行任务。
     * ·当线程数达到corePoolSize并没有线程空闲，这时再加入任务，新加的任务会被加入vorkQueue队列排
     * 队，直到有空闲的线程。
     * ·如果队列选择了有界队列，那么任务超过了队列大小时，会创建maximumPoolSize-corePoolSize数目的
     * 线程来救急。
     * ·如果线程到达maximumPoolSize仍然有新任务这时会执行拒绝策略。拒绝策略jdk提供了4种实现，其它
     * 著名框架也提供了实现
     * ·AbortPolicy让调用者抛出RejectedExecutionException异常，这是默认策略
     * ·CallerRunsPolicy让调用者运行任务
     * ·DiscardPolicy放弃本次任务
     * ·DiscardOldestPolicy放弃队列中最早的任务，本任务取而代之
     * ·Dubbo的实现，在抛出RejectedExecutionException异常之前会记录日志，并dump线程栈信息，方便定
     * 位问题
     * ·Nety的实现，是创建一个新线程来执行任务
     * ·ActiveMQ的实现，带超时等待(6Os)尝试放入队列，类似我们之前自定义的拒绝策略
     * ·PinPoint的实现，它使用了一个拒绝策略链，会逐一尝试策略链中每种拒绝策略
     * ·当高峰过去后，超过corePoolSize的救急线程如果一段时间没有任务做，需要结束节省资源，这个时间由
     * keepAliveTime和unit来控制。
     */


    /**
     * Executors 工厂类来创建线程池
     *   newFixedThreadPool   特点：没有救急线程被创建，因此也无需超时时间 ，阻塞队列是无界，可以放任意数量的任务   适用于任务量已知，相对于耗时的任务
     *   newCachedThreadPool 特点：核心线程数 0 ，最大线程数为 Integer.MAX_VALUE,救急线程的空闲生存时间是 60s,意味着
     *      - 全部都是救急线程，（60秒后可以回收）
     *      - 救急线程可以被无线创建
     *      - 队列采用了 SynchronousQueue 实现特点是，他没有容量，没有线程来取是 放不进去的 （一手交钱，一手交货）
     *      - 整个线程池表现为线程数或更具任务量不断增长，没有上限，当任务执行完毕，空闲 1 分钟后释放线程，适合任务数比较密集，但每个任务执行时间较短的情况
     *   newSingleThreadExecutor 特点：核心线程数为 1，最打线程数为 1
     *
     */

    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(2); // 一个参数的
//        ExecutorService executorService = Executors.newFixedThreadPool(2, new ThreadFactory() {
//            private AtomicInteger number = new AtomicInteger(1);
//            @Override
//            public Thread newThread(Runnable r) {
//                return new Thread(r,"myThreadPool-"+number.getAndIncrement()); // 线程工程
//            }
//        }); // 两个参数的
//        executorService.execute(()->{
//            log.info("1");
//        });
//        executorService.execute(()->{
//            log.info("2");
//        });
//        executorService.execute(()->{
//            log.info("3");
//        });
//        executorService.shutdown();
//
//        ExecutorService executorService1 = Executors.newCachedThreadPool();
//        executorService1.execute(()->{
//            log.info("1");
//        });
//        executorService1.shutdown();

        ExecutorService executorService2 = Executors.newSingleThreadExecutor(); // 始终会有一个线程去执行任务
        executorService2.execute(()->{
            log.info("1");
            int i = 1/0;
        });
        executorService2.execute(()->{
            log.info("2");
        });
        executorService2.execute(()->{
            log.info("3");
        });
    }
}
