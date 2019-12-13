package com.alan.common.help;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Alan
 * 时 间：2019-12-06
 * 简 述：<功能简述>
 */
public class JSONHelper {

    public static int getInt(JSONObject jsonObject, String key, int defaultValue) {
        if (null == jsonObject) {
            return defaultValue;
        }
        return jsonObject.optInt(key, defaultValue);
    }

    public static String getString(JSONObject jsonObject, String key, String defaultValue) {
        if (null == jsonObject) {
            return defaultValue;
        }
        return jsonObject.optString(key, defaultValue);
    }

    public static long getLong(JSONObject jsonObject, String key, long defaultValue) {
        if (null == jsonObject) {
            return defaultValue;
        }
        return jsonObject.optLong(key, defaultValue);
    }

    public static boolean getBool(JSONObject jsonObject, String key, boolean defaultValue) {
        if (null == jsonObject) {
            return defaultValue;
        }
        return jsonObject.optBoolean(key, defaultValue);
    }

    public static double getDouble(JSONObject jsonObject, String key, double defaultValue) {
        if (null == jsonObject) {
            return defaultValue;
        }
        return jsonObject.optDouble(key, defaultValue);
    }


    public static JSONObject getJSONObject(JSONObject jsonObject, String key, JSONObject defaultValue) {
        if (null == jsonObject) {
            return defaultValue;
        }
        JSONObject object = jsonObject.optJSONObject(key);
        if (null == object) {
            return defaultValue;
        }
        return object;
    }

    public static JSONArray getJSONArray(JSONObject jsonObject, String key, JSONArray defaultValue) {
        if (null == jsonObject) {
            return defaultValue;
        }
        JSONArray object = jsonObject.optJSONArray(key);
        if (null == object) {
            return defaultValue;
        }
        return object;
    }
}
