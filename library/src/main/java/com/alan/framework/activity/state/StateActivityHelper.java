package com.alan.framework.activity.state;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alan.common.Logger;
import com.alan.framework.exception.XmException;
import com.alan.framework.gloabel.Constant;

/**
 * @author Alan
 * 时 间：2019-11-21
 * 简 述：<功能简述>
 */
public class StateActivityHelper implements IStateView {

    private static final String EXCEPTION_VIEW_NULL = "root view is null";
    private static final String EXCEPTION_VIEW_NOT_FIND = "state view is not find";

    private IStateView iStateView;

    public StateActivityHelper(ViewGroup viewGroup) {
        try {
            iStateView = initStateView(viewGroup);
        } catch (XmException e) {
            Logger.e(Constant.TAG_ALAN_ERROR,e.getMessage());
        }
    }

    private IStateView initStateView(ViewGroup viewGroup) throws XmException {
        if (null == viewGroup) {
            throw new XmException(0, EXCEPTION_VIEW_NULL);
        }
        int childCount = viewGroup.getChildCount();
        if (childCount == 0) {
            throw new XmException(0, EXCEPTION_VIEW_NOT_FIND);
        } else {
            for (int i = 0; i < childCount; ++i) {
                View v = viewGroup.getChildAt(i);
                if (v instanceof IStateView) {
                    return (IStateView) v;
                }
                if (v instanceof ViewGroup) {
                    IStateView stateView = this.initStateView((ViewGroup) v);
                    if (null != stateView) {
                        return stateView;
                    }
                }
            }
            return null;
        }
    }


    @Override
    public void showLoadingState(String text) {
        iStateView.showLoadingState(text);
    }

    @Override
    public void showFailureState(String text, boolean isRetry) {
        iStateView.showFailureState(text, isRetry);
    }

    @Override
    public void showSuccessState() {
        iStateView.showSuccessState();
    }

    @Override
    public TextView getRetryView() {
        return iStateView.getRetryView();
    }
}
