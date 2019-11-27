package com.alan.framework.activity.state.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alan.framework.R;
import com.alan.framework.activity.state.ILoadingView;
import com.alan.framework.activity.state.SmileyLoadingView;


/**
 * Created by Mouse on 2018/10/22.
 */
public class CommonLoadingView extends LinearLayout implements ILoadingView {

    private SmileyLoadingView smileyLoadingView;
    private TextView textLoading;

    public CommonLoadingView(Context context) {
        super(context);
        initLayout();
    }

    public CommonLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    private void initLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.state_loading, this);
        smileyLoadingView = findViewById(R.id.loading_view);
        textLoading = findViewById(R.id.text_loading);
        setBackgroundColor(Color.WHITE);
    }

    @Override
    public void setText(String text) {
        textLoading.setText(text);
    }

    @Override
    public void start() {
        smileyLoadingView.start();
    }

    @Override
    public View getView() {
        return this;
    }
}
