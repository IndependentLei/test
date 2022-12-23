package com.shebao.test.model.entity;

import lombok.Data;

import java.util.List;

@Data
public class Person1 {
    private Long id;
    private String name;
    private String age;
    private List<Person> personList;
}
