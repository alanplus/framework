package com.alan.common;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import androidx.core.content.ContextCompat;

/**
 * @author Alan
 * 时 间：2019-11-20
 * 简 述：<功能简述>
 */
public class ResourceTools {

    public static int getResourceInteger(int res) {
        return Resources.getSystem().getInteger(res);
    }

    public static String getResourceStr(int res) {
        return Resources.getSystem().getString(res);
    }

    public static Boolean getResourceBool(int res) {
        return  Resources.getSystem().getBoolean(res);
    }

    public static int getColorFromTheme(Context context, int id, int defaultColor) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(new int[]{id});
        return typedArray.getColor(0, defaultColor);
    }

    public static boolean getBoolFromTheme(Context context, int id, boolean defaultValue) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(new int[]{id});
        return typedArray.getBoolean(0, defaultValue);
    }

    public static String getStringFromTheme(Context context, int id) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(new int[]{id});
        return typedArray.getString(0);
    }

    public static int getIntFromTheme(Context context, int id, int defaultvalue) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(new int[]{id});
        return typedArray.getInteger(0, defaultvalue);
    }

    public static float getDimissionFromTheme(Context context, int id, int defaultvalue) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(new int[]{id});
        return typedArray.getDimension(0, defaultvalue);
    }

    public static int getResourceIdFromTheme(Context context, int id, int defaultvalue) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(new int[]{id});
        return typedArray.getResourceId(0, defaultvalue);
    }
}
