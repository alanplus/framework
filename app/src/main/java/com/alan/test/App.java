package com.alan.test;

import android.app.Application;
import android.content.Context;

import com.alan.db.DatabaseConfig;
import com.alan.db.IDatabaseConfig;
import com.alan.db.base.DbModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan
 * 时 间：2019-11-26
 * 简 述：<功能简述>
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseConfig.register(this,DataBaseConfig.getInstance());
    }
}
