package com.shebao.test.test.dataStructure.skipListDemo.跳表;

import cn.hutool.core.util.RandomUtil;

public class ListTest {
    public static void main(String[] args) {
        SkipList<Integer> skipList = new SkipList<>();
        for (int i = 0; i < 20; i++) {
            int value = RandomUtil.randomInt(1, 100);
            skipList.add(new SkipNode<>(value,value));
        }
//        skipList.printf();

        skipList.add(new SkipNode<>(6,6));
    }
}
