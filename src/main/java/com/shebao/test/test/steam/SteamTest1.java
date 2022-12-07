package com.shebao.test.test.steam;

import com.shebao.test.model.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class SteamTest1 {

    @Test
    public void test1(){
        // 全面的统计信息
        List<Person> personList = Arrays.asList(new Person(1L,"小王", "2")
                , new Person(2L,"小李", "2")
                , new Person(3L,"小王", "4")
                , new Person(4L,"小春", "2")
                , new Person(4L,"小春", "4")
                , new Person(4L,"小春", "2")
                , new Person(4L,"小春", "7")
                , new Person(4L,"小春", "9")
                , new Person(4L,"小春", "2")
                , new Person(4L,"小春", "2"));

        IntSummaryStatistics collect = personList.stream().collect(Collectors.summarizingInt(p -> Integer.parseInt(p.getAge())));
        log.info("collect-->{}",collect);  // IntSummaryStatistics{count=10, sum=36, min=2, average=3.600000, max=9}

        // join的使用
        String phrase = personList
                .stream()
                .filter(p -> Integer.parseInt(p.getAge()) >= 7)
                .map(Person::getName)
                .collect(Collectors.joining(",", "[", "]"));  // [小春,小春]

        log.info("phrase-->{}",phrase);
    }

    @Test
    public void test2(){
        // 一个参数
        List<Integer> numList = Arrays.asList(1, 2, 3, 4, 5);
        Integer integer = numList.stream().reduce(new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                System.out.println("integer---->" + integer +"           "+ "integer2---->" + integer2);
                return integer + integer2;
            }
        }).get();
        System.out.println(integer);

        // 两个参数，有一个初始值,比一个参数的时候多一个运算
        Integer reduce = numList.stream().reduce(100, new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                System.out.println("integer---->" + integer + "           " + "integer2---->" + integer2);
                return integer + integer2;
            }
        });
        System.out.println(reduce);
    }

    @Test
    public void test3(){
        CopyOnWriteArrayList<Integer> accResult = Stream.of(1, 3, 5, 7).parallel()
                .reduce(new CopyOnWriteArrayList<>(),
                        new BiFunction<CopyOnWriteArrayList<Integer>, Integer, CopyOnWriteArrayList<Integer>>() {
                            @Override
                            public CopyOnWriteArrayList<Integer> apply(CopyOnWriteArrayList<Integer> integers, Integer item) {
                                System.out.println("before add: " + integers);
                                System.out.println("item= " + item);
                                integers.add(item);
                                System.out.println("after add : " + integers);
                                System.out.println("In BiFunction");
                                return integers;
                            }
                        }, new BinaryOperator<CopyOnWriteArrayList<Integer>>() {
                            @Override
                            public CopyOnWriteArrayList<Integer> apply(CopyOnWriteArrayList<Integer> integers, CopyOnWriteArrayList<Integer> integers2) {
                                integers.addAll(integers2);
                                System.out.println("integers: " + integers);
                                System.out.println("integers2: " + integers2);
                                System.out.println("In BinaryOperator");
                                return integers;
                            }
                        });
        System.out.println("accResult: " + accResult);

    }
}
