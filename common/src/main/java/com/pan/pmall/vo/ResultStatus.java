package com.pan.pmall.vo;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-02-06 12:23
 **/
public enum ResultStatus {
    SUCCESS("success", 200),
    FAILED("failed", 500),
    CREATED("created", 201),
    DELETED("deleted", 204),
    UNAUTHORIZED("unauthorized", 401),
    FORBIDDEN("forbidden", 403);

    private String msg;
    private Integer code;
    
    ResultStatus() {
    }

    ResultStatus(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }


    public Integer getCode() {
        return code;
    }
}
