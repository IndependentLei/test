package com.shebao.test.test.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 保护性暂停
 */
@Slf4j
public class GuardedObjectTest {
    public static void main(String[] args) {
        GuardedObject<String> guardedObject = new GuardedObject<>();
        new Thread(()->{
            String data = guardedObject.getData(300);
            if (Objects.isNull(data)) {
                log.info("data {}", data);
                throw new RuntimeException("执行超时");
            } else {
                log.info("data {}", data.length());
            }
        },"t1").start();

        new Thread(()->{
            StringBuilder sb = new StringBuilder();
            for (long i = 0; i < 10000000L; i++) {
                sb.append(i);
            }
           guardedObject.setData(sb.toString());
        },"t2").start();
    }
}

@Slf4j
class GuardedObject<T>{
    private T data;

    public T getData(){
        synchronized (this){
            while (data == null){
                try {
                    log.info("进入 waitSet 等待结果");
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            log.info("已经获取到结果");
            return this.data;
        }
    }

    public T getData(long times){
        synchronized (this){
            log.info("进入 while");
            long startTime = System.currentTimeMillis();
            long speedTime = 0;
            while (data == null){
                long waitTime = times - speedTime;
                log.info("waitTime {}",waitTime);
                if(waitTime <= 0){
                    break;
                }
                try {
                    log.info("进入 waitSet 等待结果");
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                speedTime = System.currentTimeMillis() - startTime;
                log.info("speedTime {}",speedTime);
            }
            log.info("已经获取到结果");
            return this.data;
        }
    }

    public void setData(T data){
        synchronized (this){
            this.data = data;
            this.notifyAll();
            log.info("赋值成功，唤醒waitSet中的线程");
        }
    }
}
