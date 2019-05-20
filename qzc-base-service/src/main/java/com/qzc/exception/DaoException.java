package com.qzc.exception;

public class DaoException extends RuntimeException {
    private static final long serialVersionUID = -2641520735078321220L;

    public DaoException(String reason) {
        super(reason);
    }
}
