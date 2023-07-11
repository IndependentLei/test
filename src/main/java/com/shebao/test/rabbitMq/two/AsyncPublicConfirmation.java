//package com.shebao.test.rabbitMq.two;
//
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.ConfirmCallback;
//import com.shebao.test.rabbitMq.utils.RabbitMqUtil;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.UUID;
//import java.util.concurrent.ConcurrentNavigableMap;
//import java.util.concurrent.ConcurrentSkipListMap;
//
///**
// * 异步发布确认
// */
//public class AsyncPublicConfirmation {
//    public static void main(String[] args) {
//        // 异步发布确认
//        asyncPublic(); // 异步确认发布花费:52ms
//    }
//
//    public static void asyncPublic(){
//        try {
//            Channel channel = RabbitMqUtil.createChannel();
//            String QueueName = UUID.randomUUID().toString();
//            channel.queueDeclare(QueueName,true,false,false,null);
//            // 开启发布确认
//            channel.confirmSelect();
//            /**
//             * 线程安全有序的哈希表,适用于高并发的情况下
//             * 1.轻松的将序号与消息进行关联
//             * 2.轻松批量删除条目，只要给到序号
//             * 3.支持高并发
//             */
//            ConcurrentSkipListMap<Long,String> outstandingConfirms = new ConcurrentSkipListMap<>();
//
//            long beginTime = System.currentTimeMillis();
//
//            // 消息确认成功，回调函数
//            /**
//             * deliveryTag:消息标识
//             * multiple:是否批量
//             */
//            ConfirmCallback ackCallback = (deliveryTag,multiple) ->{
//                if(multiple){
//                    ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(deliveryTag);
//                    confirmed.clear();
//                }else {
//                    outstandingConfirms.remove(deliveryTag);
//                }
//                System.out.println("确认的消息:"+deliveryTag);
//            };
//            // 消息确认失败，回调函数
//            ConfirmCallback nackCallback = (deliveryTag,multiple) ->{
//                String s = outstandingConfirms.get(deliveryTag);
//                System.out.println("未确认的消息标记:"+deliveryTag+"::"+"未确认的消息是"+s);
//            };
//            // 准备消息的监听器，监听哪些消息成功了，哪些消息失败了
//            channel.addConfirmListener(ackCallback,nackCallback);
//
//            for (int i = 0; i < 1000; i++) {
//                String message = "消息"+i;
//                channel.basicPublish("",QueueName,null,message.getBytes(StandardCharsets.UTF_8));
//                /**
//                 * 下一次发布的序号
//                 * 消息
//                 */
//                outstandingConfirms.put(channel.getNextPublishSeqNo(),message);
//            }
//            long endTime = System.currentTimeMillis();
//
//            System.out.println("异步确认发布花费:"+ (endTime - beginTime) +"ms");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
