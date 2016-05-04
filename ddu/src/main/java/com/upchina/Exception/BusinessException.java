package com.upchina.Exception;


public class BusinessException extends RuntimeException {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private UpChinaError error;

    public BusinessException() {
        super();
    }

    public BusinessException(UpChinaError error) {
        this.error = error;
    }

    /**
     * @return the error
     */
    public UpChinaError getError() {
        return error;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return error.code;
    }

    public String getMessage() {
        return error.message;
    }

}
