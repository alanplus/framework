package com.alan.db.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.alan.common.LogTag;
import com.alan.common.Logger;
import com.alan.db.IDatabaseConfig;
import com.alan.db.annotations.Patcher;
import com.alan.db.table.Table;
import com.alan.db.table.TableFactory;

import java.util.List;

/**
 * sqlite数据库管理类，负责表的初始化和表的更
 *
 * @author Alan
 */
public class SQLiteManager extends SQLiteOpenHelper {
    protected static volatile SQLiteManager instance;
    protected Context mContext;

    protected static IDatabaseConfig mConfig;
    protected static volatile SQLiteDatabase db;

    protected SQLiteManager(Context context, String name, int version) {
        super(context, name, null, version);
        mContext = context;
    }

    public static synchronized SQLiteDatabase getDB(Context context,
                                                    IDatabaseConfig config) {
        if (db == null) {
            mConfig = config;
            if (instance == null) {
                instance = new SQLiteManager(context, config.getDatabaseName(),
                        config.getDatabaseVersion());
            }
            db = instance.getWritableDatabase();
            try {
                String attachDatabaseName = config.getAttachDatabaseName();

                if (!TextUtils.isEmpty(attachDatabaseName)) {
                    db.execSQL(String.format("attach DATABASE '/data/data/%s/databases/%s' AS 'c'", context.getPackageName(), attachDatabaseName));
                }
            } catch (Exception e) {
                Logger.e(Log.getStackTraceString(e));
            }
        }
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        List<Class<? extends DbModel>> classes = mConfig.getTables(mContext);
        for (Class<? extends DbModel> clazz : classes) {
            try {
                Table table = TableFactory.getTable(clazz);
                String sql = table.getCreateSqlStr();
                Logger.d(sql);
                db.execSQL(sql);
                Logger.d("表" + table.getTableName());
            } catch (Exception e) {
                Logger.error(e);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        List<Class<? extends DbModel>> classes = mConfig.getTables(mContext);
        Logger.d("onUpgrade");
        for (Class<? extends DbModel> clazz : classes) {
            try {
                Patcher annotation = clazz.getAnnotation(Patcher.class);
                Class<? extends IPatcher>[] name = annotation.name();
                if (null != name && name.length > 0) {
                    for (Class<? extends IPatcher> patcher : name) {
                        try {
                            Logger.d("onUpgrade step find");
                            IPatcher inst = patcher.newInstance();
                            int max = inst.getSupportMaxVersion();
                            if (oldVersion < max) {
                                Logger.d("onUpgrade step execute");
                                inst.execute(db, clazz);
                            }
                        } catch (Exception e) {
                            Logger.error(e);
                        }
                    }
                }
            } catch (Exception e) {
                Logger.d("database update error:" + e.getMessage());
            }
        }
    }


    public static void destroy() {
        if (null != db) {
            db.close();
            db = null;
            instance = null;
        }
    }
}
