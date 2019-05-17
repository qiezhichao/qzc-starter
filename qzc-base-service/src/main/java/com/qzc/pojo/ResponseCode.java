package com.qzc.pojo;

public enum ResponseCode {
    Success("0", "请求成功"),
    Failure("1", "请求失败"),


    TOKEN_NOT_EXIST("10", "token不存在"),
    TOKEN_INVALID("11", "token无效"),

    VERIFICATION_CODE_SEND_FAILURE("20", "验证码发送失败"),
    VERIFICATION_CODE_ERROR("21", "验证码错误")

    ;


    private String code;
    private String message;

    private ResponseCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
