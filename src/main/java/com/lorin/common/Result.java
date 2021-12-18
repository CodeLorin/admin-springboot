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
    private String msg;
    private Object data;

    public static Result success(Object data, String msg) {
        return new Result(200, msg, data);
    }

    public static Result success(Object data) {
        return new Result(200, "操作成功", data);
    }

    public static Result fail(String msg) {
        return new Result(400, msg, "");
    }

    public static Result fail(Integer code, String msg) {
        return new Result(code, msg, "");
    }
}
