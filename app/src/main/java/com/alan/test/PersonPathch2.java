package com.alan.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.alan.db.base.BasePatcher;
import com.alan.db.base.DbModel;
import com.alan.db.base.IPatcher;

import java.util.HashMap;

/**
 * @author Alan
 * 时 间：2019-11-26
 * 简 述：<功能简述>
 */
public class PersonPathch2 extends BasePatcher {
    @Override
    public int getSupportMaxVersion() {
        return 2;
    }

    @Override
    public <T extends DbModel> void execute(SQLiteDatabase database, Class<T> tClass) {
        execute(database, tClass, new String[]{"score"});
    }


}
