package com.shebao.test.test.threadPool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ThreadPoolTest1 {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(1,
                1000,
                TimeUnit.MILLISECONDS,
                1,
//                ((blockingQueue, task) -> blockingQueue.put(task))
//                ((blockingQueue, task) -> blockingQueue.offer(task,2000,TimeUnit.MILLISECONDS))
//                ((blockingQueue, task) -> log.info("任务已经被放弃"))
//                ((blockingQueue, task) -> {throw new RuntimeException("任务添加失败："+task);})
                ((blockingQueue, task) -> task.run())
                /**
                 * 1、死等   ((blockingQueue, task) -> blockingQueue.put(task))
                 * 2、带超时的等待  ((blockingQueue, task) -> blockingQueue.offer(task,2000,TimeUnit.MILLISECONDS))
                 * 3、让调用这放弃任务  ((blockingQueue, task) -> log.info("任务已经被放弃"))
                 * 4、让调用这抛出异常  ((blockingQueue, task) -> {throw new RuntimeException("任务添加失败："+task);})
                 * 5、让调用者自己执行任务  ((blockingQueue, task) -> task.run())
                 */
        );
        for (int i = 0; i < 5; i++) {
            int j = i;
            threadPool.execute(()-> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.info("{}",j);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}

@Slf4j
class ThreadPool{
    // 任务队列
    private BlockingQueue<Runnable> taskQueue;
    // 线程集合
    private HashSet<Worker> workers = new HashSet<>();
    // 核心线程数
    private int coreSize;
    // 获取任务的超时时间
    private long timeout;
    // 时间单位
    private TimeUnit timeUnit;
    private RejectPolicy<Runnable> rejectPolicy;

    public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit,int dequeSize,RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.rejectPolicy = rejectPolicy;
        this.taskQueue = new BlockingQueue<>(dequeSize);
    }

    // 执行任务
    public void execute(Runnable task){
        synchronized (workers){
            if(workers.size() < coreSize){
                Worker worker = new Worker(task);
                log.info("新增worker线程：{}",worker);
                workers.add(worker);
                worker.start();
            }else {
//                taskQueue.put(task); // 一直死等
//                boolean offer = taskQueue.offer(task, timeout, timeUnit);
                /**
                 * 1、死等
                 * 2、带超时的等待
                 * 3、让调用这放弃任务在hi下
                 * 4、让调用这抛出异常
                 * 5、让调用者自己执行任务
                 */
                taskQueue.tryPut(rejectPolicy,task);
            }
        }
    }

    class Worker extends Thread{
        private Runnable task;

        public Worker(Runnable task){
            this.task = task;
        }

        @Override
        public void run() {
            // 执行任务
            // 1 当task不为空，执行任务
            // 2 当task执行完毕，在接着从任务队列中获取任务并开始执行
//            while (task != null || (task = taskQueue.take()) != null){   // 一直等待
            while (task != null || (task = taskQueue.poll(timeout,TimeUnit.MILLISECONDS)) != null){  // 有超时时间的的获取
                try {
                    log.info("正在执行：{}",task);
                    task.run();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    task = null;
                }
            }
            synchronized (workers){
                log.info("worker被移除:{}",this);
                workers.remove(this);
            }
        }
    }
}

// 拒绝策略接口
@FunctionalInterface
interface RejectPolicy<T>{
    void reject(BlockingQueue<T> blockingQueue,T task);
}


@Slf4j
class BlockingQueue<T>{
    // 容器
    private Deque<T> deque = new ArrayDeque<>();

    // 锁
    private ReentrantLock lock = new ReentrantLock();

    // 生产者条件变量
    private Condition produceWitSet = lock.newCondition();

    // 消费者条件变量
    private Condition consumerWitSet = lock.newCondition();

    // 容量
    private int size;

    public BlockingQueue(int size) {
        this.size = size;
    }

    // 带超时的获取
    public T poll(long timeOut, TimeUnit timeUnit){
        lock.lock();
        try{
            long nanos = timeUnit.toNanos(timeOut);
            while (deque.isEmpty()){
                try {
                    // 时间小于等于0，直接返回null
                    if(nanos <= 0) {
                        return null;
                    }
                    // 容器为空，进入消费者条件变量中等待
                    nanos = consumerWitSet.awaitNanos(nanos); // 返回值就是原始时间-等待时候
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            T t = deque.removeFirst();
            produceWitSet.signal();
            return t;
        }finally {
            lock.unlock();
        }

    }

    // 阻塞 获取
    public T take(){
        lock.lock();
        try{
            while (deque.isEmpty()){
                try {
                    // 容器为空，进入消费者条件变量中等待
                    consumerWitSet.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            T t = deque.removeFirst();
            produceWitSet.signal();
            return t;
        }finally {
            lock.unlock();
        }
    }

    // 阻塞 添加
    public void put(T element){
        lock.lock();
        try{
            while (deque.size() == size){
                try {
                    log.info("等待加入任务队列:{}",element);
                    // 容器为空，进入消费者条件变量中等待
                    produceWitSet.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            log.info("加入任务队列:{}",element);
            deque.addLast(element);
            consumerWitSet.signal();
        }finally {
            lock.unlock();
        }
    }

    // 有超时时间的阻塞添加
    public boolean offer(T element,long timeout,TimeUnit timeUnit){
        lock.lock();
        try{
            long nanos = timeUnit.toNanos(timeout);
            while (deque.size() == size){
                try {
                    if( nanos <= 0){
                        return false; // 返回添加失败
                    }
                    // 容器为空，进入消费者条件变量中等待
                    nanos = produceWitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            deque.addLast(element);
            consumerWitSet.signal();
            return true; // 返回添加成功
        }finally {
            lock.unlock();
        }
    }
    public void tryPut(RejectPolicy<T> rejectPolicy,T element){
        lock.lock();
        try {
            // 判断等待队列是否已经满了
            if(deque.size() == size){
                // 满了使用拒绝策略
                rejectPolicy.reject(this, element);
            }else {
                log.info("加入任务队列：{}",element);
                deque.addLast(element);
                consumerWitSet.signal();
            }
        }finally {
            lock.unlock();
        }
    }

    // 容量
    public int getSize(){
        lock.lock();
        try{
            return deque.size();
        }finally {
            lock.unlock();
        }
    }
}