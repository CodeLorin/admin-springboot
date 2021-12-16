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
    IS_BAN(401, "无访问权限"),
    NO_PERMISSION(403, "禁止"),
    PARAMS_ERROR(10001, "输入的参数有误"),
    ACCOUNT_PWD_ERROR(10002, "用户名或密码错误"),
    TOKEN_ERROR(10003, "token不合法"),
    ACCOUNT_EXIST(10004, "账号已存在"),
    SESSION_TIME_OUT(90001, "登录会话超时"),
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
