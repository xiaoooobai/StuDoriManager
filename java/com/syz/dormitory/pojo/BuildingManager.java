package com.syz.dormitory.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingManager {
    private Integer buildingId;
    private Integer managerId;

    public BuildingManager(Integer buildingId) {
        this.buildingId = buildingId;
    }
}
