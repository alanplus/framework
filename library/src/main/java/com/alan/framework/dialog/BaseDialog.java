package com.alan.framework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.alan.framework.R;


/**
 * Created by Mouse on 2018/7/17.
 */

public abstract class BaseDialog extends Dialog {


    public BaseDialog(Context context) {
        super(context, R.style.customDialog);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentRes());
        setSize(getDialogWidth());
        initView();
    }

    public abstract int getContentRes();

    public abstract void initView();


    public float getDialogWidth() {
        return 0.75f;
    }

    public void showFullScreen() {
        show();
        setSize(1.0);
    }

    protected void setSize(double widthScale) {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int windowWidth = outMetrics.widthPixels;
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) (windowWidth * widthScale); // 宽度设置为屏幕的一定比例大小
        //params.height = (int) (outMetrics.heightPixels * widthScale);
        getWindow().setAttributes(params);
    }

}
