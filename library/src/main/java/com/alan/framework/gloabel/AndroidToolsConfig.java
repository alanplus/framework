package com.alan.framework.gloabel;

import android.content.Context;
import android.os.Handler;

import com.alan.framework.LibConfig;
import com.alan.framework.activity.state.IFailureView;
import com.alan.framework.activity.state.ILoadingView;
import com.alan.framework.activity.state.view.CommonFailureView;
import com.alan.framework.activity.state.view.CommonLoadingView;
import com.alan.framework.gloabel.IActivityListener;
import com.bumptech.glide.Glide;

/**
 * Created by Mouse on 2018/10/18.
 */
public abstract class AndroidToolsConfig {


    public Context context;

    private static AndroidToolsConfig androidToolsConfig;

    public static void init(AndroidToolsConfig androidToolsConfig) {
        AndroidToolsConfig.androidToolsConfig = androidToolsConfig;
    }

    public AndroidToolsConfig(Context context) {
        this.context = context;
    }

//    /**
//     * 数据库配置
//     *
//     * @return
//     */
//    public DatabaseConfig getDatabaseConfig() {
//        return null;
//    }

    /**
     * 获取Activity管理器
     *
     * @return
     */
    public IActivityListener getIActivityListener() {
        return null;
    }

    public Handler getHandler() {
        return null;
    }

    /**
     * Loading状态的View
     * @param context
     * @return
     */
    public ILoadingView getILoadingView(Context context) {
        return new CommonLoadingView(context);
    }

    /**
     * 加载失败状态的View
     * @param context
     * @return
     */
    public IFailureView getIFailureView(Context context){
        return new CommonFailureView(context);
    }
}
