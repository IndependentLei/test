package com.shebao.test.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class TaskThreadPool {

    private static ExecutorService executorService = new ThreadPoolExecutor(5, 5, 1, TimeUnit.SECONDS, new LinkedBlockingDeque());

    public static <T> List<T> addTask(List<Callable<T>> tasks) {
        List<T> results  = new ArrayList<>();;
        if(CollectionUtils.isEmpty(tasks)){
            return results;
        }
        CompletionService completionService = new ExecutorCompletionService(executorService);
        try {
            tasks.stream().forEach(a->{
                completionService.submit(a);
            });
            for(int i=0;i<tasks.size();i++){
                Future<T> future=completionService.take();
                results.add(future.get());
            }
        } catch (Exception e) {
            log.error("addTask Exception",e);
        }
        return results;
    }
}