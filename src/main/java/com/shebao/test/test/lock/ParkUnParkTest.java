package com.shebao.test.test.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class ParkUnParkTest {
    static Thread t1;
    static Thread t2;
    static Thread t3;
    public static void main(String[] args) {
        ParKUnPark parKUnPark = new ParKUnPark(5);
        t1 = new Thread(() -> {
            parKUnPark.printf("a", t2);
        },"t1");

        t2 = new Thread(() -> {
            parKUnPark.printf("b", t3);
        },"t2");

        t3 = new Thread(() -> {
            parKUnPark.printf("c", t1);
        },"t3");

        t1.start();
        t2.start();
        t3.start();

//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        LockSupport.unpark(t1);

    }
}

class ParKUnPark{
    private Integer number;

    public ParKUnPark(Integer number){
        this.number = number;
    }

    public void printf(String name,Thread next){
        for (int i = 0; i < number; i++) {
            LockSupport.park();
            System.out.println(name);
            LockSupport.unpark(next);
        }
    }
}
