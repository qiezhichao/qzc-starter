package com.qzc.exception;

public class ControllerException extends RuntimeException {

    private static final long serialVersionUID = 3679720351612550929L;

    public ControllerException(String reason) {
        super(reason);
    }
}
