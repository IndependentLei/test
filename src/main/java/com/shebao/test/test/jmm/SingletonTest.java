package com.shebao.test.test.jmm;

public class SingletonTest {

    private static volatile Object instance = null; // 加入volatile禁止指令重排，为了防止第12行代码出现指令重排

    public static Object getInstance(){
        // 双重判断 ，单例模式，提高性能
        if(instance == null){
            synchronized (SingletonTest.class){
                if(instance == null) {
                    instance = new Object();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {

    }
}
