package com.sfs.usercenter.exception;

import com.sfs.usercenter.common.ErrorCode;



public class BusinessException extends RuntimeException{
    private final int code;
    private final String description;

    public BusinessException(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
