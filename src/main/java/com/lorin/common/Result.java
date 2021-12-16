package com.lorin.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;

/**
 * json公共返回类
 *
 * @author lorin
 * @date 2021/12/15 23:08
 */

@Data
@AllArgsConstructor
public class Result {
    private Integer code;
    private String data;
    private String msg;


    public static Result success(String data, String msg) {
        return new Result(200, data, msg);
    }

    public static Result fail(Integer code, String msg) {
        return new Result(code, null, msg);
    }
}
