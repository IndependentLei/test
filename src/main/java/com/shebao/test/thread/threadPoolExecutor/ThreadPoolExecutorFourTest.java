package com.shebao.test.thread.threadPoolExecutor;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class ThreadPoolExecutorFourTest {

    @Test
    public void test1(){
        /**
         * corePoolSize 核心线程数目 (最多保留的线程数)
         * maximumPoolSize 最大线程数目 核心线程数加救急线程数
         * keepAliveTime 生存时间 - 针对救急线程
         * unit 时间单位 - 针对救急线程
         * workQueue 阻塞队列
         * threadFactory 线程工厂 - 可以为线程创建时起个好名字
         * handler 拒绝策略
         */


        /**
         *
         *  newFixedThreadPool 固定大小线程池
         * 特点
         *
         * 核心线程数 == 最大线程数（没有救急线程被创建），因此也无需超时时间
         * 阻塞队列是无界的，可以放任意数量的任务
         * 评价 适用于任务量已知，相对耗时的任务
         * 线程池中的线程都是非守护线程 不会随着主线程的结束而结束 也可以自己实现线程工厂
         */

        /**
         *  newCachedThreadPool 带缓冲效果的
         *
         * 特点
         *
         * 核心线程数是 0， 最大线程数是 Integer.MAX_VALUE，救急线程的空闲生存时间是 60s，意味着
         * 全部都是救急线程（60s 后可以回收）
         * 救急线程可以无限创建
         * 队列采用了 SynchronousQueue 实现特点是，它没有容量，没有线程来取是放不进去的（一手交钱、一手交 货）没有取的线程 放的线程就阻塞住了
         * 评价 整个线程池表现为线程数会根据任务量不断增长，没有上限，当任务执行完毕，空闲 1分钟后释放线 程。 适合任务数比较密集，但每个任务执行时间较短的情况
         */

        /**
         * newSingleThreadExecutor
         *
         * 使用场景： 希望多个任务排队执行。线程数固定为 1，任务数多于 1 时，会放入无界队列排队。任务执行完毕，这唯一的线程 也不会被释放。
         *
         * 区别：
         *
         * 自己创建一个单线程串行执行任务，如果任务执行失败而终止那么没有任何补救措施，而线程池还会新建一 个线程，保证池的正常工作
         */
    }

    @Test
    public void test2(){
        ExecutorService pool = Executors.newFixedThreadPool(4);
        List<Callable<String>> threadList = new ArrayList<>();
        threadList.add(()->{
            log.info("1111");
            return "1";
        });
        threadList.add(()->{
            log.info("2222");
            return "2";
        });
        threadList.add(()->{
            log.info("3333");
            return "3";
        });
        try {
            List<Future<String>> futures = pool.invokeAll(threadList);
            futures.forEach(future->{
                try {
                    System.out.println(future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
