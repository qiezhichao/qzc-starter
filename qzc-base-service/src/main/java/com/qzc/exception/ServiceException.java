package com.qzc.exception;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -3160388264701192692L;

    public ServiceException(String reason) {
        super(reason);
    }
}
