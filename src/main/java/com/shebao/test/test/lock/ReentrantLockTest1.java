package com.shebao.test.test.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

//
public class ReentrantLockTest1 {
    private static final ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {

        Condition aCondition = lock.newCondition();
        Condition bCondition = lock.newCondition();
        Condition cCondition = lock.newCondition();
        PrintClass printClass = new PrintClass(5);

        new Thread(()->{
            printClass.printf("a",aCondition,bCondition);
        },"t1").start();

        new Thread(()->{
            printClass.printf("b",bCondition,cCondition);
        },"t2").start();

        new Thread(()->{
            printClass.printf("c",cCondition,aCondition);
        },"t3").start();

    }

}

class PrintClass extends ReentrantLock{
    private Integer number;

    public PrintClass(Integer number){
        this.number = number;
    }

    public void printf(String name,Condition cur,Condition next){
        for (int i = 0; i < number; i++) {
            lock();
            try {
                if(!"a".equals(name)) {
                    cur.await();
                }
                System.out.println("-----> "+name);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }

        }
    }
}