package com.syz.dormitory.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JSONUtil {

    public static void toJSON(HttpServletResponse resp, Object object) {
        try {
            resp.setContentType("text/html;charset=utf-8");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(resp.getWriter(), object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
