package com.shebao.test.test.generic.test2;

import java.util.ArrayList;
import java.util.List;

public class Test {
    //创建一个符合<T extends Comparable<T>>参数的方法
    public static <T extends Comparable<T>> void one(List<T> t){
    }

    //创建一个符合<T extends Comparable<? super T>>参数的方法
    public static <T extends Comparable<? super T>> void two(List<T> t){
    }

    public static void main(String[] args) {
        //构建list1
        List<People> list1 = new ArrayList<>();
        list1.add(new People(20));
        list1.add(new Boy(12));

        //构建list2
        List<Boy> list2 = new ArrayList<>();
        list2.add(new Boy(13));
        list2.add(new Boy(14));

        //符合预期，相当于<People extends Comparable<People>>
        Test.one(list1);
        //不符合预期，相当于<Boy extends Comparable<People>>，提示编译期错误
//        Test.one(list2);

        //符合预期，相当于<People extends Comparable<People super People>>
        Test.two(list1);
        //符合预期，相当于<Boy extends Comparable<Boy super People>>
        Test.two(list2);

        List<? super People> list = new ArrayList<>();
        Object object = list.get(0);
        List<Object> objectList = new ArrayList<>();
        list = objectList;
        Object object1 = list.get(0);

        List<People> peopleList = new ArrayList<>();
        list = peopleList;
    }
}
