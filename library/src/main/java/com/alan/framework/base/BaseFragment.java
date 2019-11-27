package com.alan.framework.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.alan.common.Logger;
import com.alan.framework.gloabel.Constant;


/**
 * Created by Mouse on 2018/11/1.
 */
public abstract class BaseFragment extends Fragment {

    protected View mRoot;
    protected Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(onCreateContent(), container, false);
        try {
            initView();
        } catch (Exception e) {
            Logger.d(Constant.TAG_ALAN_ERROR,"test_fragment e:" + Log.getStackTraceString(e));
        }
        return mRoot;
    }


    protected abstract void initView();

    protected abstract int onCreateContent();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

}
