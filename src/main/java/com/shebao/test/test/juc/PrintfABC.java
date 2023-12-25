package com.shebao.test.test.juc;

import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PrintfABC extends Thread {

    private static final ReentrantLock lock = new ReentrantLock();

    private static final Condition A = lock.newCondition();
    private static final Condition B = lock.newCondition();
    private static final Condition C = lock.newCondition();

    private static String currentStr = "A";

    private static Integer times = 0;

    private String str;
    private String nextStr;
    private Condition current;
    private Condition next;

    public PrintfABC(Condition current, Condition next, String str, String nextStr) {
        this.current = current;
        this.next = next;
        this.str = str;
        this.nextStr = nextStr;
    }

    public static void main(String[] args) {
        new PrintfABC(A, B, "A", "B").start();
        new PrintfABC(B, C, "B", "C").start();
        new PrintfABC(C, A, "C", "A").start();
    }

    @Override
    public void run() {
        while (times < 100) {
            lock.lock();
            try {
                if (Objects.equals(str, currentStr)) {
                    System.out.println(str);
                    currentStr = nextStr;
                    times++;
                    next.signal();
                } else {
                    current.await();
                }
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
        }
    }

}

