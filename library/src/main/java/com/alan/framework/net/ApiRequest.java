package com.alan.framework.net;


import androidx.annotation.IntDef;

import com.alan.common.AndroidTools;
import com.alan.framework.LibConfig;
import com.alan.framework.rx.RxSchedulers;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Mouse on 2019/4/2.
 */
public class ApiRequest {

    private ApiConfig apiConfig;

    public static final int ERROR_CODE_NO_NET = -1;
    public static final int ERROR_CODE_NO_DATA = -2;
    public static final int ERROR_CODE_HTTP = -3;

    @IntDef({ERROR_CODE_NO_NET, ERROR_CODE_NO_DATA, ERROR_CODE_HTTP})
    @interface ErrorCode {
    }

    public ApiRequest(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }


    public ApiResult executeByGet() {
        return execute(true);
    }

    public ApiResult executeByPost() {
        return execute(false);
    }

    public void executeByGetOnThread() {
        executeOnThread(true);
    }

    public void executeByPostOnThread() {
        executeOnThread(false);
    }

    public ApiResult execute(boolean isGet) {
        if (null == apiConfig) {
            return new ApiResult(ERROR_CODE_NO_DATA);
        }
        if (!AndroidTools.isNetworkAvailable(LibConfig.getApplicationContext())) {
            apiConfig.onOtherErrorCallback(ERROR_CODE_NO_NET);
            return new ApiResult(ERROR_CODE_NO_NET);
        }
        String string = isGet ? OkHttpUtil.getStrByPost(apiConfig.url(), apiConfig.params()) : OkHttpUtil.getStrByPost(apiConfig.url(), apiConfig.params());
        ApiResult apiResult = apiConfig.onResultCallback(string);
        return apiResult;
    }


    public void executeOnThread(final boolean isGet) {
        if (null == apiConfig) {
            return;
        }
        if (!AndroidTools.isNetworkAvailable(LibConfig.getApplicationContext())) {
            apiConfig.onOtherErrorCallback(ERROR_CODE_NO_NET);
            return;
        }

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) {
                String string = isGet ? OkHttpUtil.getStrByGet(apiConfig.url(), apiConfig.params()) : OkHttpUtil.getStrByPost(apiConfig.url(), apiConfig.params());
                e.onNext(string);
            }
        }).compose(RxSchedulers.<String>threadMain()).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String apiResult) {
                apiConfig.onResultCallback(apiResult);
            }
        }).onErrorReturn(new Function<Throwable, String>() {
            @Override
            public String apply(Throwable throwable) {
                apiConfig.onOtherErrorCallback(ERROR_CODE_HTTP);
                return "code:" + ERROR_CODE_HTTP;
            }
        }).subscribe();
    }
}
