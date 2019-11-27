package com.alan.framework.net;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alan.common.FileTools;
import com.alan.common.Logger;
import com.alan.framework.exception.XmException;
import com.alan.framework.gloabel.Constant;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    public static final MediaType MEDIA_TYPE_APPLICATION = MediaType.parse("application/x-www-form-urlencoded");
    private static Handler mainHandler = new Handler(Looper.getMainLooper());
    private static OkHttpClient okHttpClient;
    private static JSONObject jsonObject;

    private static final String CHARSET_NAME = "UTF-8";


    private static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            synchronized (OkHttpUtil.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(600, TimeUnit.SECONDS)//10秒连接超时
                            .writeTimeout(600, TimeUnit.SECONDS)//10m秒写入超时
                            .readTimeout(600, TimeUnit.SECONDS)//10秒读取超时
                            //.addInterceptor(new HttpHeaderInterceptor())//头部信息统一处理
                            //.addInterceptor(new CommonParamsInterceptor())//公共参数统一处理
                            .build();
                }
            }
        }
        return okHttpClient;
    }


    /**
     * 取消所有请求请求
     */
    public static void cancelAll() {
        okHttpClient.dispatcher().cancelAll();
    }

    /**
     * 不会开启异步线程。
     *
     * @param request
     * @return
     * @throws IOException
     */
    private static Response execute(Request request) throws IOException {
        return okHttpClient.newCall(request).execute();
    }

    /**
     * 开启异步线程访问网络
     *
     * @param request
     * @param responseCallback
     */
    private static void enqueue(Request request, Callback responseCallback) {
        okHttpClient.newCall(request).enqueue(responseCallback);
    }

    private static Response getResponseByGet(String url, Map<String, String> map) throws IOException {
        String param = getContentFromMap(map);
        url += "?" + param;
        Logger.d(url);
        Request request = new Request.Builder().tag(url).url(url).build();
        return execute(request);
    }

    public static String getContentFromMap(Map<String, String> params) {
        try {
            return getContentFromMap(params, true);
        } catch (UnsupportedEncodingException e) {
            Logger.e(Constant.TAG_ALAN_HTTP, Log.getStackTraceString(e));
        }
        return "";
    }

    public static String getContentFromMap(Map<String, String> params, boolean encoder) throws UnsupportedEncodingException {
        if (null == params) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Set<String> keys = params.keySet();
        for (String key : keys) {
            String value = params.get(key);
            if (value != null) {
                sb.append(key).append('=').append(encoder ? URLEncoder.encode(value, CHARSET_NAME) : value).append('&');
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        Logger.d(Constant.TAG_ALAN_HTTP, "data:" + sb.toString());
        return sb.toString();
    }

    /**
     * 同步 get 方法
     *
     * @param url
     * @param map
     * @return
     * @throws IOException
     */
    public static String getStrByGet(String url, Map<String, String> map) {
        try {
            Response response = getResponseByGet(url, map);
            if (response.isSuccessful()) {
                String responseUrl = response.body().string();
                Logger.d(Constant.TAG_ALAN_HTTP, "data:" + responseUrl);
                return responseUrl;
            }
        } catch (Exception e) {
            Logger.e(Constant.TAG_ALAN_HTTP, Log.getStackTraceString(e));
        }
        return null;
    }

    /**
     * 同步post 方法
     *
     * @param url
     * @param map
     * @return
     */
    public static String getStrByPost(String url, HashMap<String, String> map) {
        return getStrByPost(url, map, true);
    }


    public static String getStrByPost(String url, HashMap<String, String> map, boolean encoder) {
        try {
            Logger.d(Constant.TAG_ALAN_HTTP, "url:" + url);
            Response response = getResponseByPost(url, getContentFromMap(map, encoder));
            String string = response.body().string();
            Logger.d("result:" + string);
            return string;
        } catch (Exception e) {
            Logger.e(Constant.TAG_ALAN_HTTP, Log.getStackTraceString(e));
        }
        return null;
    }

    public static Response getResponseByListPost(String url, String body) throws IOException {
        Request request = new Request.Builder().url(url).post(RequestBody.create(MEDIA_TYPE_APPLICATION, body)).build();
        return okHttpClient.newCall(request).execute();
    }

    public static Response getResponseByPost(String url, String body) throws IOException {
        Request request = new Request.Builder().url(url).post(RequestBody.create(MEDIA_TYPE_APPLICATION, body)).build();
        return okHttpClient.newCall(request).execute();
    }

    public static String getStrByPost(String url, String body) {
        try {
            Response response = getResponseByPost(url, body);
            String string = response.body().string();
            Logger.d("data:" + string);
            return string;
        } catch (Exception e) {
            Logger.e(Constant.TAG_ALAN_HTTP, Log.getStackTraceString(e));
        }
        return null;
    }

    public static boolean downloadFile(String url, String path, OnDownloadFileCallback callback) {
        String name = FileTools.getFileNameByPath(url);
        return downloadFile(url, path, name, callback);
    }

    /**
     * 下载文件时，如果文件存在，则删除文件
     *
     * @param url      下载地址
     * @param name     保存的文件名
     * @param path     存储路径
     * @param callback 回调
     * @return
     */
    public static boolean downloadFile(String url, String path, String name, OnDownloadFileCallback callback) {
        String filename = name;
        String tempName = filename + ".download";
        clearFile(path, tempName);
        File file = createFile(path, tempName);
        Request request = new Request.Builder().url(url).build();
        try {
            FileOutputStream out = new FileOutputStream(file);
            Response response = okHttpClient.newCall(request).execute();
            InputStream in = response.body().byteStream();
            byte[] buf = new byte[2048];
            int len = 0;
            long current = 0;
            long total = response.body().contentLength();
            while ((len = in.read(buf)) != -1) {
                current += len;
                out.write(buf, 0, len);
                if (null != callback) {
                    callback.onDownloadFileCallback(current, total);
                }
            }
            out.flush();
            clearFile(path, filename);
            File fileDest = new File(path, filename);
            boolean b = file.renameTo(fileDest);
            if (null != callback) {
                if (b) {
                    callback.onDownloadSuccess();
                } else {
                    callback.onDownloadFailure(new XmException(0, "rename failure"));
                }
            }
            return b;
        } catch (IOException e) {
            if (null != callback) {
                callback.onDownloadFailure(e);
            }
            Logger.e(Constant.TAG_ALAN_HTTP, Log.getStackTraceString(e));
        }
        return false;
    }

    private static void clearFile(String path, String filename) {
        File file = new File(path, filename);
        if (file.exists()) {
            file.delete();
        }
    }

    private static File createFile(String path, String filename) {
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        File file = new File(path, filename);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            Logger.e(Constant.TAG_ALAN_HTTP, Log.getStackTraceString(e));
        }
        return file;
    }

}
