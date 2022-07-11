package com.shebao.test.thread.two;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Philosopher extends Thread  {
    private Scientists left;
    private Scientists right;

    public Philosopher(){}

    public Philosopher(String name , Scientists left , Scientists right){
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while(true){
            if(left.tryLock()){
                log.info("获取左筷子");
                if(right.tryLock()){
                    log.info("获取右筷子");
                    eat();
                    right.unlock();
                }
                left.unlock();
            }
        }
    }

    public void eat(){
        log.info(getName()+"--->吃饭啦！！");
    }
}
