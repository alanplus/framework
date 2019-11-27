package com.alan.framework.net;

/**
 * Created by Mouse on 2017/12/14.
 */

public interface OnDownloadFileCallback {
     void onDownloadFileCallback(long progress, long total);
     void onDownloadSuccess();
     void onDownloadFailure(Exception e);
}
