package com.alan.framework.activity;

import android.view.View;
import android.view.ViewGroup;

import com.alan.framework.activity.state.IStateConfig;
import com.alan.framework.activity.state.StateHelper;
import com.alan.framework.base.BaseActivity;
import com.alan.framework.dialog.LoadingDialog;

/**
 * @author Alan
 * 时 间：2019-11-21
 * 简 述：<功能简述>
 */
public abstract class StateBaseActivity extends BaseActivity implements IStateConfig, LoadingDialog.OnDialogDismissListener {

    protected StateHelper stateHelper;

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        stateHelper = new StateHelper(this, (ViewGroup) view, this);
    }

    @Override
    public void onDialogDismiss() {

    }

    @Override
    public String getLoadingSuccessText() {
        return StateHelper.DEFAULT_LOADING_SUCCESS_TEXT;
    }

    @Override
    public String getLoadingFailureText() {
        return StateHelper.DEFAULT_LOADING_FAILURE_TEXT;
    }

    @Override
    public LoadingDialog.OnDialogDismissListener getLoadingDismissLister() {
        return this;
    }

    public IBaseStateView getIBaseStateView() {
        return stateHelper;
    }
}
