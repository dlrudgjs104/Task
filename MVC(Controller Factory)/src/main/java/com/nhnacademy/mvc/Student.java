package com.nhnacademy.mvc;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Student {
    //아이디
    private  String id;
    //이름
    private  String name;
    //성별
    private  Gender gender;
    //나이
    private  int age;
    //생성일
    private Date createdAt;

    // ... java beans 특징을 고려하여 작성합니다.

    public Student(){

    }

    Student(String id, String name, Gender gender, int age){
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        createdAt = new Date();
    }
}

