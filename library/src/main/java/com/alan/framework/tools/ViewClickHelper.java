package com.alan.framework.tools;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxAdapterView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author Alan
 * 时 间：2019-12-03
 * 简 述：<功能简述>
 */
public class ViewClickHelper {

    /**
     * 防止重复点击
     *
     * @param target
     * @param listener
     */
    public static void preventRepeatedClick(final View target, final View.OnClickListener listener) {
        preventRepeatedClick(target, listener, 1000);
    }

    /**
     * 防止重复点击
     *
     * @param target
     * @param listener
     */
    public static void preventRepeatedClick(final View target, final View.OnClickListener listener, int millisenconds) {
        if (null == target || listener == null) {
            return;
        }
        RxView.clicks(target).throttleFirst(millisenconds, TimeUnit.MILLISECONDS).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object value) {
                listener.onClick(target);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    /**
     * 防止重复点击
     *
     * @param target
     * @param onItemClickListener
     */
    public static void preventRepeatedClick(final ListView target, final AdapterView.OnItemClickListener onItemClickListener) {
        if (null == target || onItemClickListener == null) {
            return;
        }
        RxAdapterView.itemClicks(target).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                onItemClickListener.onItemClick(target, null, value, 0);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
