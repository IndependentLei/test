package com.shebao.test.test.lock;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyThread implements Runnable {
    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();
    static int count = 3 * 100;
    String name;
    int threadId;

    public MyThread(String na, int id) {
        name = na;
        threadId = id;
    }

    public void run() {
        while (count > 0) {
            lock.lock();
            while (count % 3 == threadId) {
                System.out.println(name);
                count--;
                condition.signalAll();
            }
            try {
                if (count != 0)
                    condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Lock o = new ReentrantLock();

        Thread.sleep(500);
        new Thread(new MyThread("A", 0)).start();
        new Thread(new MyThread("B", 2)).start(); //如果是0开始逐渐++  这里就是1
        new Thread(new MyThread("C", 1)).start();

    }
}