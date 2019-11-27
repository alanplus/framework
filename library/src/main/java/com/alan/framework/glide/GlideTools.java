package com.alan.framework.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.alan.common.Logger;
import com.alan.framework.gloabel.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.target.Target;

/**
 * Created by Mouse on 2018/10/18.
 */
public class GlideTools {


    public static void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();

    }

    /**
     * 加载圆形图片
     * @param activity
     * @param imgView
     * @param url
     * @param defRes
     */
    public static void setCircleImageUrl(Context activity, ImageView imgView, String url, int defRes) {
        try {
            Glide.with(activity).load(url).placeholder(defRes).fallback(defRes).error(defRes).dontAnimate().transform(new GlideCircleTransform()).into(imgView);
        } catch (Exception e) {
            Logger.e(Constant.TAG_ALAN_ERROR,"glide tools error:" + Log.getStackTraceString(e));
        }
    }


    /**
     * 加载普通图片
     *
     * @param imgView
     * @param url
     * @param defRes
     */
    public static void setImageUrl(Context activity, ImageView imgView, String url, int defRes) {
        try {
            Glide.with(activity).load(url).dontAnimate().fallback(defRes).error(defRes).placeholder(defRes).into(imgView);
        } catch (Exception e) {
            Logger.e(Constant.TAG_ALAN_ERROR,"glide tools error:" + Log.getStackTraceString(e));
        }

    }

    /**
     * 加载资源文件
     *
     * @param imgView
     * @param imageRes
     * @param activity
     */
    public static void setImageUrl(Context activity, ImageView imgView, int imageRes) {
        try {
            Glide.with(activity).load(imageRes).dontAnimate().into(imgView);
        } catch (Exception e) {
            Logger.e(Constant.TAG_ALAN_ERROR,"glide tools error:" + Log.getStackTraceString(e));
        }
    }


    /**
     * 设置圆角图片 从资源中
     *
     * @param context
     * @param imgView
     * @param res
     */
    public static void setRoundImageUrl(Context context, ImageView imgView, int res) {
        try {
            Glide.with(context).load(res).transform(new GlideRoundTransform(5)).dontAnimate().into(imgView);
        } catch (Exception e) {
            Logger.e(Constant.TAG_ALAN_ERROR,"glide tools error:" + Log.getStackTraceString(e));
        }
    }

    /**
     * 设置圆角图片 从Url中
     *
     * @param activity
     * @param imgView
     * @param url
     * @param defRes
     */
    public static void setRoundImageUrl(Context activity, ImageView imgView, String url, int defRes) {
        try {
            Glide.with(activity).load(url).transform(new CenterCrop(), new GlideRoundTransform(5)).dontAnimate().error(defRes).fallback(defRes).placeholder(defRes).into(imgView);
        } catch (Exception e) {
            Logger.e(Constant.TAG_ALAN_ERROR,"glide tools error:" + Log.getStackTraceString(e));
        }
    }


    public static Bitmap getBitmap(String url, Context context) {
        try {
            if (TextUtils.isEmpty(url)) {
                return null;
            }

            return Glide.with(context).asBitmap().load(url).into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        } catch (Exception e) {
            Logger.e(Constant.TAG_ALAN_ERROR,e.getMessage());
        }
        return null;
    }

    /**
     * ------------------------- 带动画 --------------
     */
    /**
     * 从 url中加载 圆形 图片
     * glide 使用ApplicationContext 会导致内存泄漏
     *
     * @param activity
     * @param imgView
     * @param url
     * @param defRes
     */
    public static void setCircleImageUrlWithAnim(Context activity, ImageView imgView, String url, int defRes) {
        try {
            if (TextUtils.isEmpty(url)) {
                imgView.setImageResource(defRes);
                return;
            }
            Glide.with(activity).load(url).placeholder(defRes).transform(new GlideCircleTransform()).into(imgView);
        } catch (Exception e) {
            Logger.e(Constant.TAG_ALAN_ERROR,"glide tools error:" + Log.getStackTraceString(e));
        }
    }


    /**
     * 通过url或 文件路径 加载图片
     *
     * @param imgView
     * @param url
     * @param defRes
     */
    public static void setImageUrlWithAnim(Context activity, ImageView imgView, String url, int defRes) {
        try {
            if (TextUtils.isEmpty(url)) {
                imgView.setImageResource(defRes);
                return;
            }
            Glide.with(activity).load(url).placeholder(defRes).into(imgView);
        } catch (Exception e) {
            Logger.e(Constant.TAG_ALAN_ERROR,"glide tools error:" + Log.getStackTraceString(e));
        }
    }

    /**
     * 通过url或 文件路径 加载图片
     *
     * @param imgView
     * @param imageRes
     * @param defRes
     */
    public static void setImageUrlWithAnim(Context activity, ImageView imgView, int imageRes, int defRes) {
        try {
            Glide.with(activity).load(imageRes).placeholder(defRes).into(imgView);
        } catch (Exception e) {
            Logger.e(Constant.TAG_ALAN_ERROR,"glide tools error:" + Log.getStackTraceString(e));
        }
    }

    /**
     * 设置圆角图片 从资源中
     *
     * @param context
     * @param imgView
     * @param res
     */
    public static void setRoundImageUrlWithAnim(Context context, ImageView imgView, int res) {
        try {
            Glide.with(context).load(res).transform(new GlideRoundTransform(5)).into(imgView);
        } catch (Exception e) {
            Logger.e(Constant.TAG_ALAN_ERROR,"glide tools error:" + Log.getStackTraceString(e));
        }

    }

    /**
     * 设置圆角图片 从Url中
     *
     * @param activity
     * @param imgView
     * @param url
     * @param defRes
     */
    public static void setRoundImageUrlWithAnim(Context activity, ImageView imgView, String url, int defRes) {
        try {
            if (TextUtils.isEmpty(url)) {
                imgView.setImageResource(defRes);
                return;
            }
            Glide.with(activity).load(url).transform(new CenterCrop(), new GlideRoundTransform(5)).placeholder(defRes).into(imgView);
        } catch (Exception e) {
            Logger.e(Constant.TAG_ALAN_ERROR, "glide tools error:" + Log.getStackTraceString(e));
        }
    }

}
