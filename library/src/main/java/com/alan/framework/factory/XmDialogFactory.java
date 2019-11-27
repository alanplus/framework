package com.alan.framework.factory;

import android.content.Context;

import com.alan.framework.dialog.LoadingDialog;

/**
 * @author Alan
 * 时 间：2019-11-21
 * 简 述：<功能简述>
 */
public class XmDialogFactory {

    public static LoadingDialog showLoadingDialog(Context context, String text) {
        LoadingDialog loadingDialog = new LoadingDialog(context);
        loadingDialog.setText(text);
        loadingDialog.show();
        return loadingDialog;
    }

}
