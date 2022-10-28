package com.shebao.test.test.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest2 extends Thread{

    private static Object obj = new Object();

    private static int num = 2 * 3;

    private static int length = 3;

    private int state;
    private String sign;

    public LockTest2(int state,String sign){
        this.state = state;
        this.sign = sign;
    }

    @Override
    public void run() {
        while (num > 0){
            synchronized (obj){
                while (num % length == state){
                    System.out.println(Thread.currentThread().getName() + "---------->" + this.sign);
                    obj.notifyAll();
                    num--;
                }
                if(num != 0 ){
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new LockTest2(0,"A").start();
        new LockTest2(2,"B").start();
        new LockTest2(1,"C").start();

    }
}
