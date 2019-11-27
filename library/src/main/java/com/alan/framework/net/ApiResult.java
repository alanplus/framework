package com.alan.framework.net;

import java.util.HashMap;

/**
 * Api请求结果类
 * Title: ApiResult
 * Date: 2017/11/6 11:14
 * Company: WeiCi
 */
public class ApiResult {

    public int code;
    public String msg;
    public String url;
    public Object object;

    public HashMap<String, Object> map;

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
        this(code, msg, url, null);
    }

    public ApiResult(int code, String msg, String url, Object object) {
        this(code, msg, url, object, null);
    }

    public ApiResult(int code, String msg, String url, Object object, HashMap<String, Object> map) {
        this.code = code;
        this.msg = msg;
        this.object = object;
        this.url = url;
        this.map = map;
    }

}
