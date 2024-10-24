package com.syz.dormitory.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Building {
    Integer id;
    String tag;
    Integer roomsMax;
    String note;
    String gender;

    String managerName;
    String managerGender;

    public Building(String tag, Integer roomsMax, String note, String gender) {
        this.tag = tag;
        this.roomsMax = roomsMax;
        this.note = note;
        this.gender = gender;
    }

    public Building(Integer id, String tag, Integer roomsMax, String note, String gender) {
        this.id = id;
        this.tag = tag;
        this.roomsMax = roomsMax;
        this.note = note;
        this.gender = gender;
    }

    public Building(String tag, Integer roomsMax, String note, String gender, String managerName, String managerGender) {
        this.tag = tag;
        this.roomsMax = roomsMax;
        this.note = note;
        this.gender = gender;
        this.managerName = managerName;
        this.managerGender = managerGender;
    }
}
