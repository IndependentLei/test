package com.shebao.test.test.cas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AtomicTest2 {
    public static void main(String[] args) {
        demo(()->new int[10],
                (array)->array.length,
                (array,index)->array[index]++,
                (array)-> System.out.println(Arrays.toString(array)));

        demo(()-> new AtomicIntegerArray(10),
                AtomicIntegerArray::length,
                AtomicIntegerArray::getAndIncrement,  // 数组自选操作
                (array)-> System.out.println(array.toString()));
    }

    /**
     *
     * @param arraySupplier 提供者 无中生有 ()-> 结果
     * @param lengthFun  函数 y一个参数一个结果 (参数)-> 结果  BiFunction (参数1，参数2)-> 结果
     * @param putConsumer  消费者 一个参数没结果 (参数)-> void  BiConsumer(参数1，参数2)->void
     * @param printConsumer
     * @param <T>
     */
    private static <T> void demo(Supplier<T> arraySupplier,
                                 Function<T,Integer> lengthFun,
                                 BiConsumer<T,Integer> putConsumer,
                                 Consumer<T> printConsumer){
        List<Thread> ts = new ArrayList<>();
        T array = arraySupplier.get();
        Integer length = lengthFun.apply(array);
        for (int i = 0; i < length ; i++) {
            ts.add(new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    putConsumer.accept(array,j % length);
                }
            }));
        }
        for (Thread t : ts) {
            t.start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        printConsumer.accept(array);
    }
}
