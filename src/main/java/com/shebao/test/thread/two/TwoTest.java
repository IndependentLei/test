package com.shebao.test.thread.two;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class TwoTest {
    /**
     * 线程 1 输出 a 5 次，线程 2 输出 b 5 次，线程 3 输出 c 5 次。现在要求输出 abc abc abc abc abc 怎么实现
     */
    public static void main(String[] args) {
        LetterPrint letterPrint = new LetterPrint(15, "1");
        new Thread(()->{
            letterPrint.print("a","1","2");
        },"线程 1").start();

        new Thread(()->{
            letterPrint.print("b","2","3");
        },"线程 2").start();

        new Thread(()->{
            letterPrint.print("c","3","1");
        },"线程 3").start();

    }
}
