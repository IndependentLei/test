package com.shebao.test.test.generic.test1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GenericTest1 {
    public static void main(String[] args) {
        /**
         * 不变（Invariance）：如果 B 是 A 的子类型，那么 Generic<B> 不是 Generic<A> 的子类型。
         * 协变（Covariance）：如果 B 是 A 的子类型，那么 Generic<B> 是 Generic<A> 的子类型。
         * 逆变（Contravariance）：如果 B 是 A 的子类型，那么 Generic<A> 是 Generic<B> 的子类型（逆变就是逆转了子类型关系）。
         * 双变（Bi-variance）：Java 中不存在双变，因此不讨论。
         */


//        Print print = new Print<>();
//        Animal dog = new Dog();
//        Animal cat = new Cat();
//
//        Person person = new Person();
//
//        print.print(dog);
//        print.print(cat);
//
////        print.print(person);
//
//
//        // 协变
//        List<Animal> animalList = new ArrayList<>();
//        List<Object> objectList = new ArrayList<>();
//        List<Dog> dogList = new ArrayList<>();
////        animalList = dogList;   如果 B 是 A 的子类型，那么 Generic<B> 不是 Generic<A> 的子类型。
//
//
//        animalList.add(new Dog()); // 协变  如果 B 是 A 的子类型，那么 Generic<B> 是 Generic<A> 的子类型。
//        Animal animal = animalList.get(0);
//        System.out.println(animal.getName());
//
//        List<? super Animal> list = animalList; // 逆变  如果 B 是 A 的子类型，那么 Generic<A> 是 Generic<B> 的子类型（逆变就是逆转了子类型关系）。
//
//        list.add(new Cat());
//        list.add(new Dog());
//        list.add(new Animal() {
//            @Override
//            public String getName() {
//                return null;
//            }
//        });
//
//
//        System.out.println(list.get(0).getClass());
//        System.out.println(list.get(1).getClass());
//        System.out.println(list.get(2).getClass());


        Person[] p = new Person[3];
        p[0] = new Person(2);
        p[1] = new Person(1);
        p[2] = new Child(0);
        Person min = min(p);
        System.out.println(min);

        Child[] child = new Child[2];
        child[0] = new Child(2);
        child[1] = new Child(1);
        Person min1 = min(child);
        System.out.println(min1);

    }

    public static  <T extends Comparable<? super T>> T min(T[] a){
        Arrays.sort(a);
        return a[0];
    }

    static class Print<T extends Animal>{
        public  void print(T t){
            System.out.println(t.getName());
        }
    }

    static class Dog implements Animal{

        @Override
        public String getName() {
            return "dog";
        }
    }

    static class Cat implements Animal{

        @Override
        public String getName() {
            return "cat";
        }
    }

    static class Person implements Comparable<Person>, Animal {

        private Integer age;

        public Person(Integer age){
            this.age = age;
        }
        public Integer getAge() {
            return age;
        }

        @Override
        public  int compareTo(Person p) {
            return this.age.compareTo(p.getAge());
        }

        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    '}';
        }

        @Override
        public String getName() {
            return null;
        }
    }

    static class Child extends Person{

        public Child(Integer age) {
            super(age);
        }
    }
}
