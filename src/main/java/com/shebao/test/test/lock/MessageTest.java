package com.shebao.test.test.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;

public class MessageTest {
    public static void main(String[] args) {
        MessageQueue<Message> messageMessageQueue = new MessageQueue<>();
        messageMessageQueue.setSize(5);
        for (int i = 1; i < 10; i++) {
            int finalI = i;
            new Thread(()-> messageMessageQueue.pull(new Message(finalI,"消息："+ finalI))).start();
        }

        while (true) {
            new Thread(messageMessageQueue::take).start();
        }
    }
}

@Slf4j
class MessageQueue<T>{
    private int size;

    private LinkedList<T> conList = new LinkedList<>();

    public void setSize(int size){
        this.size = size;
    }

    public  T take(){
        synchronized (conList) {
            while (conList.isEmpty()){
                try {
                    log.info("队列为空，等待消息");
                    conList.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            T message = conList.removeFirst();
            log.info("线程：{}，消费了：{}",Thread.currentThread().getName(),message);
            conList.notifyAll();
            return message;
        }
    }

    public void pull(T msg){
        synchronized (conList) {
            while (conList.size() == size){
                try {
                    log.info("队列已满，等待消费消息");
                    conList.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            conList.addFirst(msg);
            log.info("线程：{}，生产了：{}",Thread.currentThread().getName(),msg);
            conList.notifyAll();
        }
    }
}

final class Message{
    private Integer id;
    private Object data;

    public Message(Integer id,Object data){
        this.id = id;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", data=" + data +
                '}';
    }
}
