package com.alan.framework.rx.schedule;

import com.alan.framework.net.ApiResult;

/**
 * @author Alan
 * 时 间：2019-12-05
 * 简 述：<功能简述>
 */
public abstract class SimpleScheduleRunnable implements RxScheduleRunnable<ApiResult> {

    public static final int ERROR_CODE_DEFAULT = -100;


    @Override
    public void onCompleteOnMainThread() {

    }

    @Override
    public ApiResult onError(Throwable throwable) {
        ApiResult apiResult = new ApiResult(ERROR_CODE_DEFAULT);
        scheduleOnMainThread(apiResult);
        return apiResult;
    }
}
