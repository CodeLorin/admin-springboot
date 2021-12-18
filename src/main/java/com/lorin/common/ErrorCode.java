package com.lorin.common;


/**
 * 错误代码枚举类
 *
 * @author lorin
 * @date 2021/12/16 23:42
 * @return null
 */

public enum ErrorCode {
    /**
     * 错误参数
     */
    UNAUTHORIZED(401, "登录已经过期"),
    NO_PERMISSION(403, "权限不足,禁止访问,请联系管理员"),
    PARAMS_ERROR(10001, "输入的参数有误"),
    LOGIN_ERROR(10002, "登录失败"),
    TOKEN_ERROR(10003, "token不合法"),
    TOKEN_TIMEOUT(10003, "token已过期"),
    ACCOUNT_EXIST(10005, "账号已存在"),
    FILE_NOT_SELECT(20001, "没有选择文件"),
    NO_LOGIN(90002, "未登录"),
    ;

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static void main(String[] args) {
    }
}
