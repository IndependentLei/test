package com.shebao.test.thread.three;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Data
@Slf4j
public class Visibility {
    static boolean flag = true;

    private static Object obj = new Object();

    public static void main(String[] args) {
        new Thread(()->{
            while(true){
//                synchronized (obj){
                    if(!flag){
                        log.info("进来了");
                        break;
//                    }
                }
            }
        },"线程一").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        synchronized (obj){
            flag = false;
//        }
    }
}
