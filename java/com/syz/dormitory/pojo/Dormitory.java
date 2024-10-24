package com.syz.dormitory.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dormitory {
    Integer id;
    String tag;
    Integer contain;
    String note;

    Integer buildingId;

    String buildingTag;
    String buildingGender;
    String buildingNote;
    public Dormitory(String tag, Integer contain, String note,Integer buildingId) {
        this.tag = tag;
        this.contain = contain;
        this.note = note;
        this.buildingId=buildingId;
    }

    public Dormitory(Integer id, String tag, Integer contain, String note, Integer buildingId) {
        this.id = id;
        this.tag = tag;
        this.contain = contain;
        this.note = note;
        this.buildingId = buildingId;
    }

    public Dormitory(Integer id, String tag, Integer contain, String note) {
        this.id = id;
        this.tag = tag;
        this.contain = contain;
        this.note = note;
    }
}
