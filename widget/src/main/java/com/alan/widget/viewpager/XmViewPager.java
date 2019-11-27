package com.alan.widget.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class XmViewPager extends ViewPager {
    private boolean isScrollable = true;

    public XmViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XmViewPager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isScrollable) {
            return false;
        } else {
            return super.onTouchEvent(ev);
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isScrollable) {
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    public void setScrollable(boolean scrollable) {
        isScrollable = scrollable;
    }

    public boolean isScrollable() {
        return isScrollable;
    }
}
