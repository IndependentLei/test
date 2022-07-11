package com.shebao.test.thread.two;

public class PhilosopherTest {
    public static void main(String[] args) {
        new Philosopher("小王",new Scientists("1"),new Scientists("2")).start();
        new Philosopher("小李",new Scientists("2"),new Scientists("3")).start();
//        new Philosopher("小院",new Scientists("3"),new Scientists("4")).start();
//        new Philosopher("小看",new Scientists("4"),new Scientists("5")).start();
    }
}
