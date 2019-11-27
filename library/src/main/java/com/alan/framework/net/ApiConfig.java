package com.alan.framework.net;

import java.util.HashMap;

/**
 * Created by Mouse on 2019/4/2.
 */
public interface ApiConfig {


    String url();

    HashMap<String, String> params();

    ApiResult onResultCallback(String text);

    void onOtherErrorCallback(@ApiRequest.ErrorCode int code);

}
