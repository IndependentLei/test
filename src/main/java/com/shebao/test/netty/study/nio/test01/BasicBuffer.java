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
        intBuffer.flip(); // 读写转换
        while (intBuffer.hasRemaining()){ // 是否还有数据
            System.out.println(intBuffer.get()); // 获取数据
        }
    }
}
