package com.alan.framework.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alan.common.ResourceTools;
import com.alan.common.statusbar.StatusBarTools;
import com.alan.framework.R;
import com.alan.framework.gloabel.ActivityManager;

/**
 * @author Alan
 * 时 间：2019-11-20
 * 简 述：<功能简述>
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentId());
        ActivityManager.onCreate(this);
        initStatusBar();
        initView();
    }

    protected void initStatusBar() {
        int bgColor = ResourceTools.getColorFromTheme(this, R.attr.status_bar_color, Color.WHITE);
        boolean textColorIsWhite = ResourceTools.getBoolFromTheme(this, R.attr.status_bar_text_is_white, false);
        StatusBarTools.getStatusBarTools().setStatusBarColor(this, bgColor, textColorIsWhite);
    }

    protected abstract void initView();

    @Override
    protected void onStart() {
        super.onStart();
        ActivityManager.onStart(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityManager.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityManager.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ActivityManager.onStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.onDestroy(this);
    }

    protected abstract int getContentId();

    protected Activity getActivity(){
        return this;
    }
}
