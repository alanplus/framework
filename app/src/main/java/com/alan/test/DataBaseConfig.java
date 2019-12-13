package com.alan.test;

import android.content.Context;

import com.alan.db.IDatabaseConfig;
import com.alan.db.base.DbModel;
import com.alan.db.model.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan
 * 时 间：2019-11-27
 * 简 述：<功能简述>
 */
public class DataBaseConfig implements IDatabaseConfig {

    private static DataBaseConfig dataBaseConfig;

    public static DataBaseConfig getInstance(){
        if(null==dataBaseConfig){
            dataBaseConfig = new DataBaseConfig();
        }
        return dataBaseConfig;
    }

    @Override
    public String getDatabaseName() {
        return "app.db";
    }

    @Override
    public int getDatabaseVersion() {
        return 1;
    }

    @Override
    public List<Class<? extends DbModel>> getTables(Context context) {
        List<Class<? extends DbModel>> classList = new ArrayList<>();
        classList.add(Person.class);
        classList.add(Config.class);
        return classList;
    }

    @Override
    public String getAttachDatabaseName() {
        return "weici.db";
    }
}
