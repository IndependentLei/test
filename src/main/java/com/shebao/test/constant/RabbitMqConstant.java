package com.shebao.test.constant;

/**
 * @Description : rabbitMq配置类
 * @ClassName : RabbitMqConstant
 * @Author : jdl
 * @Create : 2022-07-03 22:59
 */
public class RabbitMqConstant {
    // 普通交换机的名称
    public static final  String X_EXCHANGE = "X";

    // 死信交换机的名称
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    // 死信routing key
    public static final String Y_DEAD_LETTER_ROUTING_KEY = "YD";

    // 普通队列的名称
    public static final String QUEUE_A = "QA";
    public static final String X_ROUTING_KEY_QA = "XA";
    public static final String QUEUE_B = "QB";
    public static final String QUEUE_C = "QC";
    public static final String X_ROUTING_KEY_QB = "XB";
    public static final String X_ROUTING_KEY_QC = "XC";

    // 死信队列的名称
    public static final String DEAD_LETTER_QUEUE = "QD";
}
