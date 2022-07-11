package com.shebao.test.thread.one;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@Slf4j
public class MessageQueue {
    private  LinkedList<Message> linkedList = Lists.newLinkedList();

    private int capacity;

    public MessageQueue(int capacity){
        this.capacity = capacity;
    }

    public void put(Message message){
        synchronized (linkedList){
            while (linkedList.size() == capacity){
                try {
                    log.info("队列已满！！！");
                    // 等待
                    linkedList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            linkedList.addFirst(message);
            log.info("消息已存入，message:{}",message);
            // 唤醒其他阻塞线程
            linkedList.notifyAll();
        }
    }

    public Message take(){
        synchronized (linkedList){
            while (linkedList.isEmpty()){
                log.info("队列为空！！！");
                // 等待
                try {
                    linkedList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message message = linkedList.removeLast();
            log.info("已消费一个，message:{}",message);
            linkedList.notifyAll();
            return message;
        }
    }

}
