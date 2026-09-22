package com.KaraveddyConnect.exception;

/**
 * @author Anuruththan-local
 * @org Smartzi
 * @since 2026-09-22 17:53 PM
 **/
public class BusinessException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    protected int code;

    protected String message;


    public BusinessException() {
    }

    /**
     * @param cause cause
     * @param code  code
     */
    public BusinessException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    /**
     * @param message message
     * @param cause   cause
     * @param code    code
     */
    public BusinessException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    /**
     * @param message message
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * @param code code
     */
    public BusinessException(int code) {
        this.code = code;
    }

    /**
     * @return int code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @param message message
     * @param code    code
     */
    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }
}
