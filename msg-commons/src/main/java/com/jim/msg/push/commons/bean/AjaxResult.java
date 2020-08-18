package com.jim.msg.push.commons.bean;

import lombok.Builder;

import java.io.Serializable;
import java.util.Collections;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-18 14:45
 */
@Builder
public class AjaxResult<T> implements Serializable {


    private static final long serialVersionUID = 1024312584282751553L;

    private boolean success;    // 是否成功
    private int code;            // 状态码
    private int totalCount;
    private String msg;            // 消息
    private T data;        // 数据
    private String returnObject;

    public AjaxResult(boolean success, int code, int totalCount, String msg, T data, String returnObject) {
        this.success = success;
        this.code = code;
        this.totalCount = totalCount;
        this.msg = msg;
        this.data = data;
        this.returnObject = returnObject;
    }

    public AjaxResult() {
    }

    public static <T> AjaxResult NoData(){
        return new AjaxResult(true, 200, 0, "没有数据", null, null);
    }

    public static <T> AjaxResult successNoData4emtyList(){
        return new AjaxResult(true, 200, 0, "没有数据", Collections.emptyList(), null);
    }

    public static <T> AjaxResult success() {

        return new AjaxResult(true, 200, 0, null, null, null);
    }

    public static <T> AjaxResult success(T data) {

        return new AjaxResult(true, 200, 0, null, data, null);
    }

    public static <T> AjaxResult success(T data, int totalCount) {

        return new AjaxResult(true, 200, totalCount, null, data, null);
    }

    public static <T> AjaxResult success(int code, T data) {
        return new AjaxResult(true, code, 0, null, data, null);
    }

    public static <T> AjaxResult success(int code, String msg,T data) {
        return new AjaxResult(true, code, 0, msg, data, null);
    }

    public static <T> AjaxResult successMsg(String msg) {
        return new AjaxResult(true, 200, 0, msg, null, null);
    }

    public static <T> AjaxResult fail(String msg) {
        return new AjaxResult(false, 500, 0, msg, null, null);
    }

    public static <T> AjaxResult fail(int code, String msg) {
        return new AjaxResult(false, code, 0, msg, null, null);
    }

    public static <T> AjaxResult fail(int code, String msg, T data) {
        return new AjaxResult(false, code, 0, msg, data, null);
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(String returnObject) {
        this.returnObject = returnObject;
    }



    @Override
    public String toString() {
        return "AjaxResult{" +
                "successMsg=" + success +
                ", code=" + code +
                ", totalCount=" + totalCount +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", returnObject='" + returnObject + '\'' +
                '}';
    }

}