package com.alan.common.drawable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;

import com.alan.common.AndroidTools;

/**
 * @author Alan
 * 时 间：2019-11-20
 * 简 述：<功能简述>
 */
public class DrawableFactory {

    public static Drawable generateCornerDrawable(int color, int radius) {
        int i = AndroidTools.dip2px(radius);
        float[] out = new float[]{i, i, i, i, i, i, i, i};
        ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(out, null, null));
        drawable.getPaint().setColor(color);
        return drawable;
    }

}
