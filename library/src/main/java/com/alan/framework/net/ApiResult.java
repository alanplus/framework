package com.alan.framework.net;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Api请求结果类
 * Title: ApiResult
 * Date: 2017/11/6 11:14
 * Company: WeiCi
 */
public class ApiResult<T> {

    public int code;
    public String msg;
    public String url;
    public T t;

    public HashMap<String, Object> map;

    protected JSONObject jsonObject;

    public int type;

    public ApiResult() {
    }

    public ApiResult(int code) {
        this(code, null);
    }

    public ApiResult(int code, String msg) {
        this(code, msg, null);
    }

    public ApiResult(int code, String msg, String url) {
        this.code = code;
        this.msg = msg;
        this.url = url;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
