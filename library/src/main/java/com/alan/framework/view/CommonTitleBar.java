package com.alan.framework.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.alan.common.AndroidTools;
import com.alan.common.ResourceTools;
import com.alan.common.ViewTools;
import com.alan.common.drawable.DrawableFactory;
import com.alan.framework.R;


/**
 * Created by Mouse on 2018/6/18.
 */

public class CommonTitleBar extends RelativeLayout {

    private TextView title, rightText;
    private ImageButton imageButtonBack;

    private String titleStr;
    private boolean enableDivider;
    private View divider;

    private String rightTextStr;
    private int righttextSize;
    private int righttextColor;
    private int righttextColorPress;
    private int iconRes;

    private int leftIconRes;
    private @ColorInt
    int bgColor;

    private @ColorInt
    int titleColor;

    private float titleTextSize;

    /**
     * 0 隐藏右侧所有元素
     * 1 隐藏 icon 显示文本
     * 2 隐藏 文本 显示icon
     */
    private int rightShowType;

    private Drawable leftDrawable, topDrawable, rightDrawable, bottomDrawable;
    private float drawablePadding;


    @Override
    public void removeOnUnhandledKeyEventListener(OnUnhandledKeyEventListener listener) {
        super.removeOnUnhandledKeyEventListener(listener);
    }

    public CommonTitleBar(Context context) {
        this(context, null);
    }

    public CommonTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        init();
    }

    private void initAttrs(AttributeSet attributeSet) {
        enableDivider = true;
        if (null != attributeSet) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.CommontTitleBarView);

            leftIconRes = typedArray.getResourceId(R.styleable.CommontTitleBarView_bar_back_icon, getDefaultBackIcon());
            bgColor = typedArray.getColor(R.styleable.CommontTitleBarView_bar_bg_color, getDefaultBgColor());

            titleStr = typedArray.getString(R.styleable.CommontTitleBarView_bar_title);
            titleColor = typedArray.getColor(R.styleable.CommontTitleBarView_bar_title_color, getDefaultTitleColor());
            titleTextSize = typedArray.getDimension(R.styleable.CommontTitleBarView_bar_title_size, getDefaultTitleSize());

            enableDivider = typedArray.getBoolean(R.styleable.CommontTitleBarView_divider_enable, getDefaultEnableDivider());

            rightShowType = typedArray.getInt(R.styleable.CommontTitleBarView_bar_right_show_type, 0);
            rightTextStr = typedArray.getString(R.styleable.CommontTitleBarView_bar_right_text);
            righttextSize = (int) typedArray.getDimension(R.styleable.CommontTitleBarView_bar_right_text_size, getDefaultRightTextSize());
            righttextColor = typedArray.getColor(R.styleable.CommontTitleBarView_bar_right_text_color, getDefaultRightTextColor());
            righttextColorPress = typedArray.getColor(R.styleable.CommontTitleBarView_bar_right_text_color_press, getDefaultRightPressTextColor());
            iconRes = typedArray.getResourceId(R.styleable.CommontTitleBarView_bar_right_icon, 0);

            leftDrawable = typedArray.getDrawable(R.styleable.CommontTitleBarView_bar_right_left_icon);
            topDrawable = typedArray.getDrawable(R.styleable.CommontTitleBarView_bar_right_top_icon);
            rightDrawable = typedArray.getDrawable(R.styleable.CommontTitleBarView_bar_right_right_icon);
            bottomDrawable = typedArray.getDrawable(R.styleable.CommontTitleBarView_bar_right_bottom_icon);
            drawablePadding = typedArray.getDimension(R.styleable.CommontTitleBarView_bar_right_drawable_padding, AndroidTools.dip2px( 3));

            typedArray.recycle();
        }
    }

    private int getDefaultBackIcon() {
        return ResourceTools.getResourceIdFromTheme(getContext(), R.attr.title_bar_back_icon, R.drawable.icon_title_bar_back);
    }

    private int getDefaultBgColor() {
        return ResourceTools.getColorFromTheme(getContext(), R.attr.title_bar_color, Color.WHITE);
    }

    private int getDefaultTitleColor() {
        return ResourceTools.getColorFromTheme(getContext(), R.attr.title_bar_title_color, Color.parseColor("#535353"));
    }

    private float getDefaultTitleSize() {
        return ResourceTools.getDimissionFromTheme(getContext(), R.attr.title_bar_title_size, AndroidTools.dip2px(18));
    }

    private boolean getDefaultEnableDivider() {
        return ResourceTools.getBoolFromTheme(getContext(), R.attr.title_bar_enable_divider, true);
    }

    private float getDefaultRightTextSize() {
        return ResourceTools.getDimissionFromTheme(getContext(), R.attr.title_bar_right_text_size, AndroidTools.dip2px(16));
    }

    private int getDefaultRightTextColor() {
        return ResourceTools.getColorFromTheme(getContext(), R.attr.title_bar_right_text_color, Color.parseColor("#535353"));
    }

    private int getDefaultRightPressTextColor() {
        return ResourceTools.getColorFromTheme(getContext(), R.attr.title_bar_right_text_color_press, Color.parseColor("#535353"));
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_common_titlebar, this);
        setBackgroundColor(bgColor);
        initBackButton();
        initDivider();
        initRightView();
        initTitle();
    }

    private void initBackButton() {
        imageButtonBack = findViewById(R.id.icon_back);
        imageButtonBack.setImageResource(leftIconRes);
        imageButtonBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() instanceof Activity) {
                    ((Activity) getContext()).finish();
                }
            }
        });
    }

    private void initTitle() {
        title = findViewById(R.id.main_tilte);
        title.setTextColor(titleColor);
        title.setText(titleStr);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
        if (rightShowType == 1) {
            int targetWidth = ViewTools.getTargetWidth(rightText);
            int i = AndroidTools.dip2px(48);
            int max = Math.max(targetWidth, i);
            LayoutParams lp = (LayoutParams) title.getLayoutParams();
            lp.leftMargin = max + AndroidTools.dip2px(10);
            lp.rightMargin = max + AndroidTools.dip2px( 10);
        }
    }

    private void initDivider() {
        divider = findViewById(R.id.divider_line);
        divider.setVisibility(enableDivider ? View.VISIBLE : View.GONE);
    }

    private void initRightView() {
        rightText = findViewById(R.id.right_text);
        switch (rightShowType) {
            case 0:
                findViewById(R.id.right_view).setVisibility(View.GONE);
                break;
            case 1:
                findViewById(R.id.right_view).setVisibility(View.VISIBLE);
                findViewById(R.id.view1).setVisibility(View.GONE);
                rightText.setVisibility(View.VISIBLE);
                rightText.setText(rightTextStr);
                ColorStateList colorStateList = DrawableFactory.buildClickTextColor(righttextColor, righttextColorPress);
                rightText.setTextColor(colorStateList);
                rightText.setTextSize(TypedValue.COMPLEX_UNIT_PX, righttextSize);
                break;
            case 2:
                findViewById(R.id.right_view).setVisibility(View.VISIBLE);
                findViewById(R.id.view1).setVisibility(View.VISIBLE);
                rightText.setVisibility(View.GONE);
                findViewById(R.id.icon1).setBackgroundResource(iconRes);
                break;
        }
        setRightTextDrawable();
    }

    private void setRightTextDrawable() {
        if (null == leftDrawable && topDrawable == null && rightDrawable == null && bottomDrawable == null) {
            return;
        }
        setDrawableBound(leftDrawable);
        setDrawableBound(topDrawable);
        setDrawableBound(rightDrawable);
        setDrawableBound(bottomDrawable);
        rightText.setCompoundDrawables(leftDrawable, topDrawable, rightDrawable, bottomDrawable);
        rightText.setCompoundDrawablePadding((int) drawablePadding);
    }

    private void setDrawableBound(Drawable drawable) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
    }

    public void setOnRightViewClickListener(OnClickListener onRightViewClickListener) {
        findViewById(R.id.right_view).setOnClickListener(onRightViewClickListener);
    }

    public void showRightText(String text, OnClickListener onClickListener) {
        this.rightTextStr = text;
        initRightView();
        setOnRightViewClickListener(onClickListener);
    }

    public void setRightTextSize(int textSize) {
        this.righttextSize = textSize;
        rightText.setTextSize(TypedValue.COMPLEX_UNIT_PX, righttextSize);
    }

    public void setRighttextColor(@ColorInt int color) {
        this.righttextColor = color;
        rightText.setTextColor(color);
    }


    public void setTitle(String text) {
        title.setText(text);
    }

    public void setTitle(String text, OnClickListener onClickListener) {
        setTitle(text);
        title.setOnClickListener(onClickListener);
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getRightText() {
        return rightText;
    }

    public ImageButton getImageButtonBack() {
        return imageButtonBack;
    }

    public void showDivider(boolean show) {
        divider.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }
}
