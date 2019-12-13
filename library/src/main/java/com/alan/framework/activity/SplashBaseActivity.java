package com.alan.framework.activity;

import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.alan.framework.R;
import com.alan.framework.base.BaseActivity;

/**
 * @author Alan
 * 时 间：2019-11-27
 * 简 述：<功能简述>
 */
public class SplashBaseActivity extends BaseActivity {

    @Override
    protected void initView() {
        if (canStartActivity()) {
            new Handler().postDelayed(this::startMainActivity, getDelayTime());
        }

    }

    protected boolean canStartActivity() {
        return true;
    }

    protected void startMainActivity() {

    }

    protected int getDelayTime() {
        return 1500;
    }


    @Override
    public void setContentView(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.setContentView(view);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_splash;
    }
}
