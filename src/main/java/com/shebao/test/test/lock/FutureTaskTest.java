package com.shebao.test.test.lock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {
    public static void main(String[] args) {
        try {
            FutureTask<Integer> futureTask = new FutureTask<>(new MyCallable());
            futureTask.run();
            System.out.println(futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    static class MyCallable implements Callable<Integer>{

        @Override
        public Integer call() throws Exception {
            System.out.println("----------> callabel");
            return 1024;
        }
    }
}
