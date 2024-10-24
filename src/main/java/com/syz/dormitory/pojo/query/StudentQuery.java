package com.syz.dormitory.pojo.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentQuery {
    private Integer page;
    private Integer limit;
    private String name;
    private String gender;

    private String major;
    private String class_;
    private Integer dormitoryId;

    public StudentQuery(Integer page, Integer limit, String name, String gender, String major, String class_) {
        this.page = page;
        this.limit = limit;
        this.name = name;
        this.gender = gender;
        this.major = major;
        this.class_ = class_;
    }
}
