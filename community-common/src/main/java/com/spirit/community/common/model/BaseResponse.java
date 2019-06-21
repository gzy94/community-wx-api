/**
 * @Title: BaseResponse.java
 * @Package com.madiot.activiti.console.model
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2018/10/8
 * @version
 */
package com.spirit.community.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName: BaseResponse
 * @Description: 基础响应对象
 * @author Yi.Wang2
 * @date 2018/10/8
 */
@ApiModel(value = "baseResponse", description = "基础响应对象")
public class BaseResponse<T> {

    @ApiModelProperty(value = "请求返回编码说明", name = "message", example = "异常")
    private String message;

    @ApiModelProperty(value = "请求返回编码", name = "code", example = "00001")
    private String code;

    @ApiModelProperty(value = "数据信息", name = "result")
    private T result;

    public BaseResponse() {
        this.code = BaseResultCode.SUCCESS.getCode();
        this.message = BaseResultCode.SUCCESS.getMsg();
    }

    public BaseResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 执行成功时构造方法
     * @param result 执行结果
     */
    public BaseResponse(T result) {
        this.code = BaseResultCode.SUCCESS.getCode();
        this.message = BaseResultCode.SUCCESS.getMsg();
        this.result = result;
    }

    /**
     * 执行成功时构造方法
     * @param result 执行结果
     * @param message 提示信息
     */
    public BaseResponse(T result, String message) {
        this.code = BaseResultCode.SUCCESS.getCode();
        this.message = message;
        this.result = result;
    }

    /**
     * 执行异常时构造方法
     * @param e 异常信息
     */
    public BaseResponse(ErrorCodeException e) {
        this.message = e.getMessage();
        this.code = e.getErrorCode();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.code != null && BaseResultCode.SUCCESS.getCode().equals(this.code);
    }
}
