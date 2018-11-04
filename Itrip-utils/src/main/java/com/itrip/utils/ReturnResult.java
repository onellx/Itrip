package com.itrip.utils;

/**
 * 响应前端结果信息的一个封装工具类
 */
public class ReturnResult {
    /**
     * 状态码 取值范围：整数
     */
    private Integer status;
    /**
     * 向客户端结果信息
     */
    private String message;
    /**
     * 向客户端传递的数据
     */
    private Object result;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }


}
