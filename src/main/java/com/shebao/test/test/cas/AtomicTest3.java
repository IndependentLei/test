package com.shebao.test.test.cas;

import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AtomicTest3 {
    public static void main(String[] args) {
        demo(AtomicLong::new,
                AtomicLong::incrementAndGet);  // 37

        demo(LongAdder::new,  // 原子累加器  ,设置了多个累加单元，Cell
                LongAdder::increment);  // 12
        /**
         *     transient volatile Cell[] cells;  累加单元数据，懒惰初始化
         *
         *      transient volatile long base;  基础值，如果没有竞争，则用 cas累加这个域
         *
         *      transient volatile int cellsBusy; 在 cells 创建或扩容时，为1，表示加锁
         */
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    private static <T> void demo(Supplier<T> supplier,
                                 Consumer<T> consumer){
        List<Thread> tsList = new ArrayList<>();
        T t = supplier.get();
        for (int i = 0; i < 4; i++) {
            tsList.add(new Thread(()->{
                for (int j = 0; j < 500000; j++) {
                    consumer.accept(t);
                }
            }));
        }

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("task1");
        for (Thread thread : tsList) {
            thread.start();
        }

        for (Thread thread : tsList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        stopWatch.stop();
        System.out.println(t);
        System.out.println(stopWatch.getTotalTimeMillis());
    }
}
