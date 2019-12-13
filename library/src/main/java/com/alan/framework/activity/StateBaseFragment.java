package com.alan.framework.activity;

import android.view.ViewGroup;

import com.alan.framework.activity.state.IStateConfig;
import com.alan.framework.activity.state.StateHelper;
import com.alan.framework.base.BaseFragment;
import com.alan.framework.dialog.LoadingDialog;

/**
 * @author Alan
 * 时 间：2019-11-21
 * 简 述：<功能简述>
 */
public abstract class StateBaseFragment extends BaseFragment implements IBaseStateView, IStateConfig, LoadingDialog.OnDialogDismissListener {

    protected StateHelper stateHelper;

    @Override
    protected void initView() {
        stateHelper = new StateHelper(activity, (ViewGroup) mRoot, this);
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

    @Override
    public void onDialogDismiss() {

    }

    @Override
    public void showLoadingState() {
        stateHelper.showLoadingState();
    }

    @Override
    public void showLoadingState(String text) {
        stateHelper.showLoadingState(text);
    }

    @Override
    public void showFailureState(String text, boolean isRetry) {
        stateHelper.showFailureState(text,isRetry);
    }

    @Override
    public void showSuccessState() {
        stateHelper.showSuccessState();
    }

    @Override
    public void showLoadingDialog(String dialog) {
        stateHelper.showLoadingState(dialog);
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
