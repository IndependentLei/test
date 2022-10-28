package com.shebao.test.test.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest1 extends Thread{
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    private static int num = 2 * 3;

    private static int length = 3;

    private int state;
    private String sign;

    public LockTest1 (int state,String sign){
        this.state = state;
        this.sign = sign;
    }

    @Override
    public void run() {
            while (num > 0){
                lock.lock();
                while (num % length == state){
                    System.out.println(sign);
                    num--;
                    condition.signalAll();
                }
                try {
                    if( num != 0 ){
                        condition.await();
                    }
                }catch (Exception e){

                }finally {
                    System.out.println("--------------->");
                    lock.unlock();
                }
            }

    }

    public static void main(String[] args) {
        new LockTest1(0,"A").start();
        new LockTest1(2,"B").start();
        new LockTest1(1,"C").start();
    }
}
