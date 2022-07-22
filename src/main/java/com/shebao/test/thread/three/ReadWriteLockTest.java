package com.shebao.test.thread.three;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class ReadWriteLockTest {
    private static  Object data ;
    private static final ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    private static final ReentrantReadWriteLock.ReadLock r = rw.readLock(); // 读
    private static final ReentrantReadWriteLock.WriteLock w= rw.writeLock(); // 写


    public Object read(){
        log.debug("获取读锁");
        r.lock();
        try {
            log.debug("读取");
            return data;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            log.debug("释放读锁");
            r.unlock();
        }
        return null;
    }

    public void write(){
        log.debug("获取写锁");
        w.lock();
        try {
            log.debug("写入");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            log.debug("释放写锁");
            w.unlock();
        }
    }
}
