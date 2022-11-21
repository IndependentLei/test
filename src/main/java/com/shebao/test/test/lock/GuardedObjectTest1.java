package com.shebao.test.test.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class GuardedObjectTest1 {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Person().start();
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Enumeration<Integer> ids = MailBox.getIds();
        while (ids.hasMoreElements()){
            Integer id = ids.nextElement();
            new PostMan<>(id,"送件："+id).start();
        }
    }
}

@Slf4j
class Person extends Thread{
    @Override
    public void run() {
        log.info("Person start ");
        GuardedObj guardedObj = MailBox.genGuardedObj();
        Object data = guardedObj.getData();
        log.info("id:{},已经获取到信件：{}",guardedObj.getId(),data);
    }
}

@Slf4j
class PostMan<T> extends Thread{
    private Integer id;
    private T data;

    public PostMan (Integer id,T data){
        this.id = id;
        this.data = data;
    }

    @Override
    public void run() {
        log.info("Person start ");
        GuardedObj guardedObj = MailBox.getGuardedObj(id);
        guardedObj.setData(data);
    }
}


@Slf4j
class MailBox<T>{
    private static ConcurrentHashMap<Integer,GuardedObj> guardedObjMap = new ConcurrentHashMap<>();
    private static AtomicInteger num = new AtomicInteger(1);

    public static int addNum(){
        return num.getAndIncrement();
    }

    public static GuardedObj genGuardedObj(){
        GuardedObj guardedObj = new GuardedObj(addNum());
        log.info("genGuardedObj start id {}",guardedObj.getId());
        guardedObjMap.put(guardedObj.getId(),guardedObj);
        return guardedObj;
    }

    public static GuardedObj getGuardedObj(Integer id){
        return guardedObjMap.remove(id);
    }

    public static Enumeration<Integer> getIds(){
        return guardedObjMap.keys();
    }

}


@Slf4j
class GuardedObj<T>{

    private Integer id;

    public GuardedObj(Integer id){
        this.id = id;
    }
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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