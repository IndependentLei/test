package com.shebao.test.test.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class FutureTest1 {
    public static void main(String[] args) {
//        submit();
//        invokeAll();
        invokeAny();
    }

    public static void submit(){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<String> submit = executorService.submit(() -> {
            log.info("running");
            TimeUnit.SECONDS.sleep(1);
            return "ok";
        });

        String s = null;
        try {
            s = submit.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println(s);
        executorService.shutdown();
    }

    public static void invokeAll(){
        List<Callable<String>> list = new ArrayList<>();
        list.add(()-> "1");
        list.add(()-> "2");
        list.add(()-> "3");

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        try {
            List<String> result = new ArrayList<>();
            List<Future<String>> futures = executorService.invokeAll(list);
            for (Future<String> future : futures) {
                result.add(future.get());
            }
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }finally {
            executorService.shutdown();
        }
    }

    public static void invokeAny(){
        List<Callable<String>> list = new ArrayList<>();
        list.add(()-> "1");
        list.add(()-> "2");
        list.add(()-> "3");

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        try {
            // 哪个任务先成功执行完毕，返回此任务执行结果，其它任务取消
            String any = executorService.invokeAny(list);
            System.out.println(any);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }finally {
            executorService.shutdown();
        }
    }
}
