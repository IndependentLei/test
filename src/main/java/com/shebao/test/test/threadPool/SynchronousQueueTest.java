package com.shebao.test.test.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SynchronousQueueTest {
    public static void main(String[] args) {
        // SynchronousQueue
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();

        new Thread(()->{
            try {
                log.info("start put {}",1);
                synchronousQueue.put(1);
                log.info("end put {}",1);

                log.info("start put {}",2);
                synchronousQueue.put(2);
                log.info("end put {}",2);
            }catch (Exception e){
                e.printStackTrace();
            }
        },"t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(()->{
            try {
                log.info("start take {}",1);
                synchronousQueue.take();
                log.info("end take {}",1);
            }catch (Exception e){
                e.printStackTrace();
            }
        },"t2").start();
    }
}
