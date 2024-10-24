package com.syz.dormitory.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    Integer id;
    String name;
    Integer age;
    String major;
    String class_;
    String phone;
    String gender;

    Integer dorId;
    String dorTag;
    Integer buildingId;
    String buildingTag;

    public Student(String name, Integer age, String major, String class_, String phone, String gender) {
        this.name = name;
        this.age = age;
        this.major = major;
        this.class_ = class_;
        this.phone = phone;
        this.gender = gender;
    }

    public Student(Integer id, String name, Integer age, String major, String class_, String phone, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.major = major;
        this.class_ = class_;
        this.phone = phone;
        this.gender = gender;
    }
}
