package com.shebao.test.test.guava;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;

public class test {
    public static void main(String[] args) {
        ImmutableList<String> immutableList = ImmutableList.of("1", "2", "3");
        System.out.println(immutableList);

        Multimap<String, Integer> map = ArrayListMultimap.create();
        map.put("aa", 1);
        map.put("aa", 1);

        System.out.println(map.get("aa"));
    }
}
