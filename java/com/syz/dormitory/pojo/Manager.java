package com.syz.dormitory.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manager {
    Integer id;
    String name;
    String gender;

    String image;
    public Manager(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    public Manager(String name, String gender, String image) {
        this.name = name;
        this.gender = gender;
        this.image = image;
    }

    public Manager(Integer id, String name, String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }
}
