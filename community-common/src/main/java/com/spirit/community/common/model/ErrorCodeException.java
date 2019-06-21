/**
 * @Title: BaseResponseException.java
 * @Package com.madiot.activiti.console.common
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2018/10/8
 * @version
 */
package com.spirit.community.common.model;

/**
 * @ClassName: BaseResponseException
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2018/10/8
 */
public class ErrorCodeException extends Exception {

    private String errorCode;

    public ErrorCodeException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCodeException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCodeException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
