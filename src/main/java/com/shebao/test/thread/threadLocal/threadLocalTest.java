package com.shebao.test.thread.threadLocal;

import org.junit.Test;

public class threadLocalTest {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Test
    public void test9(){
        setThreadLocal();
    }

    private void setThreadLocal(){
        threadLocal.set("111");
        getThreadLocal();
    }

    private void getThreadLocal(){
        String str = threadLocal.get();
        System.out.println(str);
    }
}
