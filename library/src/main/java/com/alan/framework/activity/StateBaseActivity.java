package com.alan.framework.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alan.framework.activity.state.IStateConfig;
import com.alan.framework.activity.state.StateHelper;
import com.alan.framework.base.BaseActivity;
import com.alan.framework.dialog.LoadingDialog;

/**
 * @author Alan
 * 时 间：2019-11-21
 * 简 述：<功能简述>
 */
public abstract class StateBaseActivity extends BaseActivity implements IBaseStateView, IStateConfig, LoadingDialog.OnDialogDismissListener {

    protected StateHelper stateHelper;

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        stateHelper = new StateHelper(this, (ViewGroup) view, this);
        initRetryView();
    }

    protected void initRetryView() {
        TextView retryView = stateHelper.getIStateView().getRetryView();
        if (retryView != null) {
            retryView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRetryViewClickListener();
                }
            });
        }

    }


    protected void onRetryViewClickListener() {

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

    @Override
    public void showLoadingState() {
        stateHelper.showLoadingDialog(this);
    }

    @Override
    public void showLoadingState(String text) {
        stateHelper.showLoadingState(text);
    }

    @Override
    public void showFailureState(String text, boolean isRetry) {
        stateHelper.showFailureState(text, isRetry);
    }

    @Override
    public void showSuccessState() {
        stateHelper.showSuccessState();
    }

    @Override
    public void showLoadingDialog(String dialog) {
        stateHelper.showLoadingDialog(dialog);
    }

    @Override
    public void dismissLoadingDialog(boolean isSuccess) {
        stateHelper.dismissLoadingDialog(isSuccess);
    }

    @Override
    public void showToast(String text) {
        stateHelper.showToast(text);
    }

}
