package com.shebao.test.model.entity;

import lombok.Data;

import java.util.List;

@Data
public class TestPerson {
    private List<Person1> personList;
    private String name;
}
