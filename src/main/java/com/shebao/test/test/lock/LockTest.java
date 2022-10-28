package com.shebao.test.test.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
    private static long shuzi = 0;
    private ReentrantLock reentrantLock = new ReentrantLock(); // 非公平锁
    private Condition condition = reentrantLock.newCondition();
    public static void main(String[] args) {

        LockTest lockTest = new LockTest();
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                lockTest.add();
            }
        },"AAAAA").start();

        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                lockTest.sub();
            }
        },"BBBBB").start();

        System.out.println("最终结果是："+shuzi);
    }

    private void add(){
        reentrantLock.lock();
        try {
            if(shuzi != 0){
                condition.await();
            }
            System.out.println("线程：------->"+Thread.currentThread().getName()+"自加了1---------->"+(++shuzi));
            condition.signal();
        } catch (Exception e) {

        } finally {
            reentrantLock.unlock();
        }
    }

    private void sub(){
        reentrantLock.lock();
        try {
            if(shuzi != 1){
                condition.await();
            }
            System.out.println("线程：------->"+Thread.currentThread().getName()+"自减了1----------->"+(--shuzi));
            condition.signal();
        } catch (Exception e) {

        } finally {
            reentrantLock.unlock();
        }
    }
}
