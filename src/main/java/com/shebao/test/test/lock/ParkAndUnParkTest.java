package com.shebao.test.test.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class ParkAndUnParkTest {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            LockSupport.park();
            System.out.println("结束了");
        });

        t1.start();

        LockSupport.unpark(t1);

        System.out.println("main 结束了");

        // unpark 可以在 park 之前使用
    }
}
