package com.shebao.test.netty.study.nio.test01;

import java.nio.IntBuffer;

public class BasicBuffer {
    public static void main(String[] args) {
        // 创建一个 Buffer ，大小为5，即可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 向buffer存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }
        // 取出数据
        /**
         * public final Buffer flip() {
         *         limit = position;
         *         position = 0;
         *         mark = -1;
         *         return this;
         *     }
         */
        intBuffer.flip(); // 读写转换
        intBuffer.position(1); // 设置 position位置
        intBuffer.limit(3); // 设置 缓冲区的终点
        while (intBuffer.hasRemaining()){ // 是否还有数据
            System.out.println(intBuffer.get()); // 获取数据
        }
    }
}
