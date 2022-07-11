package com.shebao.test.thread.two;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * // 暂停当前线程
 * LockSupport.park();
 *
 * // 恢复某个线程的运行
 * LockSupport.unpark(暂停线程对象)
 */

@Slf4j
public class LockSupportTest {
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            log.info("进入t1线程");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LockSupport.park();
            log.info("come on !");
        },"t1");

        t1.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("main go!!!");

        LockSupport.unpark(t1);
    }
}
