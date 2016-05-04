package com.upchina.Exception;


public class TradeException extends RuntimeException {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private String code;
    private String message;

    public TradeException() {
        super();
    }

    public TradeException(String code,String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
