package com.shebao.test.test.generic.test2;

public class People implements Comparable<People>{
    private Integer age;

    public People(Integer age) {
        this.age = age;
    }

    @Override
    public int compareTo(People people) {
        return this.age-people.age;
    }
}