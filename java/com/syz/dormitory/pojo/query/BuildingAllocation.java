package com.syz.dormitory.pojo.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingAllocation {
    Integer id;
    String tag;
    Integer roomsMax;
    Integer hasAllocation;
    Integer studentNum;

    public BuildingAllocation(String tag, Integer roomsMax, Integer hasAllocation) {
        this.tag = tag;
        this.roomsMax = roomsMax;
        this.hasAllocation = hasAllocation;
    }

    public BuildingAllocation(Integer id, String tag, Integer roomsMax, Integer hasAllocation) {
        this.id = id;
        this.tag = tag;
        this.roomsMax = roomsMax;
        this.hasAllocation = hasAllocation;
    }

    public BuildingAllocation(Integer id, String tag, Integer studentNum) {
        this.id = id;
        this.tag = tag;
        this.studentNum = studentNum;
    }
}
