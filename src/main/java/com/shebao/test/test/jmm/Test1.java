package com.shebao.test.test.jmm;

public class Test1 {
    static volatile boolean run = true; // 每次都在主存中获取run的，保证 可见行、禁止指令重排，不保证原子性
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (run) { // 此线程经常用到 run，就会从主存储中copy一份过来
//                System.out.println(11111);
                /**
                 * JMM（java内存模型）如何保证可见性，加入读屏障和写屏障，写屏障之前的写入操作都同步到主存中，读屏障之后的读取都去主存中读取
                 * JMM（java内存模型）如果保证有序性，加入读屏障和写屏障，写屏障之前的指令不会被排到之后，读屏障之后的指令不会被排到读屏障之前
                 */
            }
        }, "t1");

        t1.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        run = false;
        System.out.println("t1线程停止");
    }

}
