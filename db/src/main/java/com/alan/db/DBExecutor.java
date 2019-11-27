package com.alan.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alan.common.Logger;
import com.alan.common.reflect.ObjectFactory;
import com.alan.db.base.DbModel;
import com.alan.db.base.SQLiteManager;
import com.alan.db.converters.ColumnConverterFactory;
import com.alan.db.converters.IColumnConverter;
import com.alan.db.table.Column;
import com.alan.db.table.Table;
import com.alan.db.table.TableFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alan
 * 时 间：2019-11-26
 * 简 述：数据库执行类
 */
public class DBExecutor {

    private static final String[] ALL_COLS = new String[]{"*"};

    /**
     * 插入数据库对象
     *
     * @param t
     * @param <T>
     * @return
     */
    public static synchronized <T extends DbModel> boolean insert(T t) {
        ContentValues contentValues = getContentValues(t);
        Table table = t.getTable();
        return getSQLiteDatabase().insert(table.getTableName(), null, contentValues) > 0;
    }


    /**
     * 批量插入
     *
     * @param list
     * @param <T>
     * @return
     */
    public static synchronized <T extends DbModel> boolean insert(List<T> list) {
        SQLiteDatabase sqLiteDatabase = getSQLiteDatabase();
        sqLiteDatabase.beginTransaction();
        for (T t : list) {
            if (!insert(t)) {
                return false;
            }
        }
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        return true;
    }

    /**
     * 根据主键替换数据库对象
     *
     * @param t
     * @param <T>
     * @return
     */
    public static synchronized <T extends DbModel> boolean replace(T t) {
        ContentValues contentValues = getContentValues(t);
        Table table = t.getTable();
        return getSQLiteDatabase().replace(table.getTableName(), null, contentValues) > 0;
    }

    /**
     * 批量替换
     *
     * @param list
     * @param <T>
     */
    public static synchronized <T extends DbModel> void replace(List<T> list) {
        SQLiteDatabase sqLiteDatabase = getSQLiteDatabase();
        sqLiteDatabase.beginTransaction();
        for (T t : list) {
            replace(t);
        }
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    /**
     * 根据主键替换数据库对象
     *
     * @param t
     * @param columns 根据对象的列名
     * @return
     */
    public static synchronized <T extends DbModel> boolean update(T t, String... columns) {
        ContentValues contentValues = getContentValues(t);
        Table table = t.getTable();
        StringBuilder stringBuilder = new StringBuilder();
        String[] strings = whereArgs(t, stringBuilder, columns);
        return getSQLiteDatabase().update(table.getTableName(), contentValues, stringBuilder.toString(), strings) > 0;
    }

    /**
     * 根据主键 删除对象
     *
     * @param t
     * @param <T>
     * @return
     */
    public static synchronized <T extends DbModel> boolean delete(T t, String... columns) {
        Table table = t.getTable();
        StringBuilder stringBuilder = new StringBuilder();
        String[] strings = whereArgs(t, stringBuilder, columns);
        return getSQLiteDatabase().delete(table.getTableName(), stringBuilder.toString(), strings) > 0;
    }

    /**
     * 批量删除
     *
     * @param columns
     * @param list
     * @param <T>
     * @return
     */
    public static synchronized <T extends DbModel> boolean delete(String[] columns, T... list) {
        SQLiteDatabase sqLiteDatabase = getSQLiteDatabase();
        sqLiteDatabase.beginTransaction();
        for (T t : list) {
            if (!delete(t, columns)) {
                return false;
            }
        }
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        return true;
    }

    /**
     * 删除表中的所有数据
     *
     * @param tClass
     */
    public static synchronized void deleteAll(Class<? extends DbModel>... tClass) {
        SQLiteDatabase sqLiteDatabase = getSQLiteDatabase();
        sqLiteDatabase.beginTransaction();
        for (Class<? extends DbModel> t : tClass) {
            String tableName = TableFactory.getTableName(t);
            getSQLiteDatabase().delete(tableName, null, null);
        }
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }


    public static synchronized <T extends DbModel> List<T> findAll(Class<T> tClass) {
        Table table = TableFactory.getTable(tClass);
        String sql = "select * from " + table.getTableName();
        return findList(tClass, sql);
    }

    public static synchronized <T extends DbModel> T find(Class<T> tClass, HashMap<String, String> map) {
        return find(tClass, map, null, null, null, null);
    }

    public static synchronized <T extends DbModel> List<T> findList(Class<T> tClass, HashMap<String, String> map) {
        return findList(tClass, map, null, null, null, null);
    }

    /**
     * 根据列表查询对象
     *
     * @param tClass
     * @param map
     * @param <T>
     * @return
     */
    public static synchronized <T extends DbModel> T find(Class<T> tClass, HashMap<String, String> map, String groupBy, String having, String orderBy, String limit) {
        Table table = TableFactory.getTable(tClass);
        StringBuilder stringBuilder = new StringBuilder();
        String[] objects = getQueryParams(stringBuilder, map);
        Cursor cursor = getSQLiteDatabase().query(table.getTableName(), ALL_COLS, stringBuilder.toString(), objects, groupBy, having, orderBy, limit);
        return getItem(tClass, cursor);
    }

    public static synchronized <T extends DbModel> List<T> findList(Class<T> tClass, HashMap<String, String> map, String groupBy, String having, String orderBy, String limit) {
        Table table = TableFactory.getTable(tClass);
        StringBuilder stringBuilder = new StringBuilder();
        String[] objects = getQueryParams(stringBuilder, map);
        Cursor cursor = getSQLiteDatabase().query(table.getTableName(), ALL_COLS, stringBuilder.toString(), objects, groupBy, having, orderBy, limit);
        return getList(tClass, cursor);
    }

    private static synchronized String[] getQueryParams(StringBuilder stringBuilder, HashMap<String, String> map) {
        String[] objects = new String[map.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" and ");
            }
            stringBuilder.append(entry.getKey()).append("=?");
            objects[i++] = entry.getValue();
        }
        return objects;
    }

    private static synchronized <T extends DbModel> T getItem(Class<T> tClass, Cursor cursor) {
        ObjectFactory<T> factory = new ObjectFactory<>(tClass);
        Table table = TableFactory.getTable(tClass);
        try {
            if (null != cursor && cursor.getCount() > 0 && cursor.moveToFirst()) {
                return getModelByCursor(table, cursor, factory);
            }
            return null;
        } catch (Exception e) {
            Logger.error(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    private static synchronized <T extends DbModel> List<T> getList(Class<T> tClass, Cursor cursor) {
        List<T> list = new ArrayList<>();
        try {
            if (null != cursor && cursor.getCount() > 0) {
                ObjectFactory<T> factory = new ObjectFactory<>(tClass);
                Table table = TableFactory.getTable(tClass);
                while (cursor.moveToNext()) {
                    T t = getModelByCursor(table, cursor, factory);
                    if (t != null) {
                        list.add(t);
                    }
                }
            }
        } catch (Exception e) {
            Logger.error(e);
        } finally {
            cursor.close();
        }
        return list;
    }

    /**
     * 查找一个对象
     *
     * @param tClass
     * @param sql
     * @param <T>
     * @return
     */
    public static synchronized <T extends DbModel> T find(Class<T> tClass, String sql) {
        Cursor cursor = getSQLiteDatabase().rawQuery(sql, null);
        return getItem(tClass, cursor);
    }

    /**
     * 查找多个对象
     *
     * @param tClass
     * @param sql
     * @param <T>
     * @return
     */
    public static synchronized <T extends DbModel> List<T> findList(Class<T> tClass, String sql) {
        Cursor cursor = getSQLiteDatabase().rawQuery(sql, null);
        return getList(tClass, cursor);
    }

    private static synchronized <T extends DbModel> T getModelByCursor(Table table, Cursor cursor, ObjectFactory<T> objectFactory) throws Exception {
        T t = objectFactory.newInstance();
        Map<String, Column> map = table.getColumns();
        for (int columnIndex = 0, count = cursor.getColumnCount(); columnIndex < count; columnIndex++) {
            String columnName = cursor.getColumnName(columnIndex);
            // 通过字段名字得到对应的java类中的字段
            Column column = map.get(columnName);
            if (column != null) {
                // 得到字段的转化器
                IColumnConverter converter = column.getColumnConverter();
                // 从cursor中得到值
                Object value = converter.getDBType().getValueFromCursor(cursor, columnIndex);
                // 从sql类型的数据转化成java类保存的数据类型
                value = converter.toJavaValue(value);
                // 把value值设置在result实例里
                column.getColumnField().set(t, value);
            }
        }
        return t;
    }

    private static synchronized SQLiteDatabase getSQLiteDatabase() {
        return SQLiteManager.getDB(DatabaseConfig.context, DatabaseConfig.iDatabaseConfig);
    }

    public static synchronized <T extends DbModel> String[] whereArgs(T t, StringBuilder stringBuilder, String... columns) {
        List<String> list = new ArrayList<>();
        if (columns == null) {
            Collection<Column> values = t.getTable().getColumns().values();
            for (Column column : values) {
                if (column.isPrimaryKey()) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(" and ");
                    }
                    stringBuilder.append(column.getName()).append("=?");
                    list.add(getValue(t, column));
                }
            }

        } else {
            Map<String, Column> map = t.getTable().getColumns();
            for (String columnStr : columns) {
                Column column = map.get(columnStr);
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(" and ");
                }
                stringBuilder.append(columnStr).append("=?");
                list.add(getValue(t, column));
            }
        }
        String[] array = new String[list.size()];
        list.toArray(array);
        return array;
    }

    private static synchronized <T extends DbModel> String getValue(T t, Column column) {
        try {
            return convertToStringValues(column.getColumnField().get(t));
        } catch (IllegalAccessException e) {
            Logger.error(e);
        }
        return "";
    }

    private static synchronized <T extends DbModel> ContentValues getContentValues(T t) {
        ContentValues contentValues = new ContentValues();
        Table table = t.getTable();
        Collection<Column> values = table.getColumns().values();
        for (Column column : values) {
            if (column.isAutoIncrement()) {
                continue;
            }
            contentValues.put(column.getName(), getValue(t, column));
        }
        return contentValues;
    }

    private static String convertToStringValues(Object value) {
        /**
         * 把绑定的值转化为 字符串形式的绑定的值，但是必须先进行转化器转化一下
         */
        // 得到对应类型的转化器
        IColumnConverter converter = ColumnConverterFactory.getColumnConverter(value);
        // 从java类型的数据转化成sql保存的数据类�?
        value = converter.toSqlValue(value);
        // 转化为字符串
        return String.valueOf(value);
    }
}
