package com.syz.dormitory.pojo.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DormitoryQuery {
    private Integer page;
    private Integer limit;
    private String tag;
    private String buildingTag;
}
