package com.qzc.pojo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class Response {

    private Object data;

    private String code;

    private String message;

    private String requestId;

    private Response(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private Response(Object data, String code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public static Response ok() {
        return new Response(ResponseCode.Success.getCode(),ResponseCode.Success.getMessage());
    }

    public static Response ok(String message) {
        return new Response(ResponseCode.Success.getCode(),
                            StringUtils.isBlank(message) ? ResponseCode.Success.getMessage() : message);
    }

    public static Response ok(Object data, String message) {
        return new Response(data,
                            ResponseCode.Success.getCode(),
                            StringUtils.isBlank(message) ? ResponseCode.Success.getMessage() : message);
    }

    public static Response failure(String message) {
        return new Response(ResponseCode.Failure.getCode(),
                            StringUtils.isBlank(message) ? ResponseCode.Failure.getMessage() : message);
    }

    public static Response failure(ResponseCode responseCode) {
        return new Response(responseCode.getCode(), responseCode.getMessage());
    }

    public static Response failure(ResponseCode responseCode, String message) {
        return new Response(responseCode.getCode(),
                            StringUtils.isBlank(message) ? responseCode.getMessage() : message);
    }
}
