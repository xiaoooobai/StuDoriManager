package com.syz.dormitory.pojo.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuery {
    private Integer page;
    private Integer limit;
    private String username;


    @Override
    public String toString() {
        return "UserQuery{" +
                "page=" + page +
                ", limit=" + limit +
                ", name='" + username + '\'' +
                '}';
    }
}
