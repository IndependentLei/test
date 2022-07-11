package com.shebao.test.thread.two;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入索
 */
@Slf4j
public class ReentrantLockTest {
    private ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        new ReentrantLockTest().method01();
    }

    public void method01(){
        lock.lock();
        log.info("方法一");
        method02();
        lock.unlock();
    }

    public void method02(){
        lock.lock();
        log.info("方法二");
        method03();
        lock.unlock();
    }

    public void method03(){
        lock.lock();
        log.info("方法三");
        lock.unlock();
    }
}
