package com.shebao.test.thread.one;

public class OneTest {
    public static void main(String[] args) {

        MessageQueue messageQueue = new MessageQueue(3);

        for(int i = 0 ; i < 3 ; i++){
            Message message = new Message(i,"消息："+i);
            new Thread(()-> messageQueue.put(message), "生产者线程"+i).start();
        }
        new Thread(messageQueue::take, "消费者线程").start();
    }
}
