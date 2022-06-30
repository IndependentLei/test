package com.shebao.test.test;

import com.alibaba.excel.EasyExcel;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.shebao.test.model.entity.Person;
import com.shebao.test.model.entity.Person1;
import com.shebao.test.model.entity.TestPerson;
import com.shebao.test.model.enums.TypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;
import org.springframework.beans.BeanUtils;


import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class test {
//    public static void main(String[] args) {
//        Person person = Person.builder()
//                        .name("jdl")
//                        .age("2")
//                        .build();
//        System.out.println(person.toString());
//    }

    @Test
    public void test2(){
        List<String> strings = Collections.singletonList("222");
        System.out.println(strings);
    }

    @Test
    public void test3(){
        Integer integer = 128;
        int i = 128;
        boolean equals = Objects.equals(integer, i);
        System.out.println(equals);
    }

//    @Test
//    public void test4() throws IOException {
//        String filePath = TestFileUtil.getPath()+"excel"+File.separator+"template.xlsx";
//        String newFileName = TestFileUtil.getPath()+UUID.randomUUID() +".xlsx";
//        com.shebao.test.model.entity.Test test = new com.shebao.test.model.entity.Test();
//        test.setNumber(22L);
//        EasyExcel.write(newFileName).withTemplate(filePath).sheet().doFill(test);
//    }

    @Test
    public void test5(){
        List<Person> personList = Arrays.asList(new Person(1L,"小王", "2")
                , new Person(2L,"小李", "2")
                , new Person(3L,"小王", "2")
                , new Person(4L,"小春", "2"));
        Map<String, List<Person>> collect = personList.stream().collect(Collectors.groupingBy(Person::getName));
        System.out.println(collect);
        System.out.println(collect.get("小王"));
    }

    @Test
    public void test6(){
        String str = "1,2,3,4";
        List<String> strList = Splitter.on(",").splitToList(str);
        System.out.println(strList);
    }

    public static void main(String[] args) {
        String str = "1,2,3,4";
        List<String> strList = Splitter.on(",").splitToList(str);
        System.out.println(strList);
    }

    @Test
    public void test7(){
        List<Person> personList = Arrays.asList(new Person(1L,"小王", "2")
                , new Person(2L,"小李", "2")
                , new Person(3L,"小王", "2")
                , new Person(4L,"小春", "2"));
        Map<String, String> collect = personList.stream().collect(Collectors.toMap(Person::getName, Person::getAge, (x, y) -> y));
        System.out.println(collect);

    }

    @Test
    public void test8(){
        List<Integer> typeList = Arrays.asList(1, 1, 2, 1);
        List<String> collect = typeList.stream().map(type -> TypeEnum.fromCode(type).getName()).collect(Collectors.toList());
        String join = Joiner.on(",").join(collect);
        System.out.println(join);
    }

    @Test
    public void test9(){
        List<Long> ids = Lists.newArrayList();
        System.out.println(ids);
        if(CollectionUtils.isNotEmpty(ids)){
            System.out.println(1);
        }

        HashMap<String, String> A = Maps.newHashMap();
        HashMap<String, String> B = Maps.newHashMap();

        Map<String, Object> collect = A.entrySet()//获取集合
                .stream()//获取流
                .filter(a->B.keySet().contains(a))//peek支持在每个元素上执行一个操作并且返回新的stream
                // ，我们就利用这个方法转换数据
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));//collect方法用来将流转到集合对象
    }

    @Test
    public void test10(){
        Map<String,String> one = Maps.newHashMap();
        one.put("1","2");
        one.put("2","3");
        one.put("3","4");
        Map<String,String> two = Maps.newHashMap();
        two.put("3","4");
        two.put("4","5");
        two.put("6","7");

        Map<String, String> collectMap = one.entrySet().stream().filter(o -> two.containsKey(o.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println(collectMap);

    }


    @Test
    public void test11(){
        List<Person> personList = Arrays.asList(new Person(1L,"小王", "2")
                , new Person(2L,"小李", "2")
                , new Person(3L,"小王", "2")
                , new Person(4L,"小春", "2"));

        TestPerson testPerson = new TestPerson();
        TestPerson testPerson1 = new TestPerson();
        Person1 person1 = new Person1();
        person1.setPersonList(personList);
        Person1 person11 = new Person1();
        person11.setPersonList(personList);
        List<Person1> person1List = Lists.newArrayList();
        person1List.add(person1);
        person1List.add(person11);
        testPerson.setPersonList(person1List);
        testPerson.setName("111");

        BeanUtils.copyProperties(testPerson,testPerson1);
        System.out.println(testPerson1);
    }
}
