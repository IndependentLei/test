package com.shebao.test.test.threadPool;

public class ThreadPoolExecutorTest2 {
    /**
     * //执行任务
     * void execute(Runnable command);
     *
     * //提交任务task,用返回值Future获得任务执行结果
     * <T>Future<T>submit(Callable<T>task);
     *
     * //提交task5中所有任务
     * <T>List<Future<T>>invokeAll(Collection<?extends Callable<T>>tasks)
     * throws InterruptedException;
     *
     * //提交task5中所有任务，带超时时间
     * <T>List<Future<T>>invokeAll(Collection<?extends Callable<T>>tasks,
     * long timeout,TimeUnit unit)
     * throws InterruptedException;
     *
     * //提交tāsks中所有任务，哪个任务先成功执行完毕，返回此任务执行结果，其它任务取消
     * <T>T invokeAny(Collection<?extends Callable<T>>tasks)
     * throws InterruptedException,ExecutionException;
     *
     * //提交tsk5中所有任务，哪个任务先成功执行完毕，返回此任务执行结果，其它任务取消，带超时时间
     * <T>T invokeAny(Collection<?extends Callable<T>>tasks,
     * long timeout,TimeUnit unit)
     * throws InterruptedException,ExecutionException,TimeoutException;
     */
}
