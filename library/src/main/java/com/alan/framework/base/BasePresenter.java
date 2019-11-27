package com.alan.framework.base;

import android.content.Context;
import android.content.Intent;


import com.alan.framework.activity.IBaseStateView;

/**
 * @author Alan
 * 时 间：2019-11-21
 * 简 述：<功能简述>
 */
public abstract class BasePresenter<T extends IBaseStateView>{

    protected T iBaseStateView;
    protected Context context;

    public BasePresenter(Context context, T iBaseStateView) {
        this.iBaseStateView = iBaseStateView;
        this.context = context;
    }

    public abstract boolean init(Intent intent);

}
