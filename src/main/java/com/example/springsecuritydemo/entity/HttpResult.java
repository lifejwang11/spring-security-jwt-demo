package com.example.springsecuritydemo.entity;


public class HttpResult {

    private int code = 200;
    private String msg;
    private Object data;

    public static HttpResult error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static HttpResult error(String msg) {
        return error(500, msg);
    }

    public static HttpResult error(String msg, Object data) {
        return error(500, msg, data);
    }

    public static HttpResult auth(int code, String msg) {
        HttpResult r = new HttpResult();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static HttpResult error(int code, String msg) {
        HttpResult r = new HttpResult();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static HttpResult error(int code, String msg, Object data) {
        HttpResult r = new HttpResult();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static HttpResult ok(Object data) {
        HttpResult r = new HttpResult();
        r.setData(data);
        r.setMsg("Success");
        return r;
    }

    public static HttpResult ok(String msg,Object data) {
        HttpResult r = new HttpResult();
        r.setData(data);
        r.setMsg(msg);
        return r;
    }

    public static HttpResult ok() {
        HttpResult r = new HttpResult();
        r.setMsg("Success");
        return r;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
