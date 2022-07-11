package com.shebao.test.thread.two;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class LetterPrint extends ReentrantLock {
    public int LetterPrintLength;
    private  String step;
    public void print(String str , String nowStep , String nextStep){
        for (int i = 0; i < LetterPrintLength; i++) {
            synchronized (this){
                while (!step.equals(nowStep)){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(str);
                step = nextStep;
                this.notifyAll();
            }
        }
    }

    public LetterPrint(int letterPrintLength){
        this.LetterPrintLength = letterPrintLength;
    }

    public void print1(String str, Condition current , Condition next){

        for (int i = 0; i < LetterPrintLength ; i++) {
            lock();
            try {
                current.await();
//                log.info("进来了:{}",str);
                System.out.print(str);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }

    public void print2(String str,Thread next){
        for (int i = 0; i < LetterPrintLength; i++) {
            LockSupport.park();
            System.out.print(str);
            LockSupport.unpark(next);
        }
    }

    static Thread a;
    static Thread b;
    static Thread c;

    public static void main(String[] args) {
        LetterPrint letterPrint = new LetterPrint(5);
//        Condition a = letterPrint.newCondition();
//        Condition b = letterPrint.newCondition();
//        Condition c = letterPrint.newCondition();
//        new Thread(()->{
//            letterPrint.print1("a",a,b);
//        },"线程一").start();
//
//        new Thread(()->{
//            letterPrint.print1("b",b,c);
//        },"线程二").start();
//
//        new Thread(()->{
//            letterPrint.print1("c",c,a);
//        },"线程三").start();
//
//
//        try {
//            TimeUnit.SECONDS.sleep(1);
//            letterPrint.lock();
//            a.signal();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        letterPrint.unlock();

        a = new Thread(()->{
            letterPrint.print2("a",b);
        },"线程一");

        b = new Thread(()->{
            letterPrint.print2("b",c);
        },"线程二");

        c = new Thread(()->{
            letterPrint.print2("c",a);
        },"线程三");

        a.start();
        b.start();
        c.start();

        LockSupport.unpark(a);
    }
}
