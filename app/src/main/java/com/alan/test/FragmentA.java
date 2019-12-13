package com.alan.test;

import android.text.Html;
import android.widget.TextView;

import com.alan.framework.base.BaseFragment;

/**
 * @author Alan
 * 时 间：2019-11-28
 * 简 述：<功能简述>
 */
public class FragmentA extends BaseFragment {
    @Override
    protected void initView() {
        String text ="<p>&nbsp; &nbsp; &nbsp;A study from the University of California at Davis suggested that replacing petrol-powered private cars worldwide with electric, self-driving and shared systems could reduce carbon emissions from transportation 80% and cut the cost of transportation infrastructure (基础设施) and operations 40% by 2050. Fewer emissions and cheaper travel sound pretty appealing. The first commercially available driverless cars will almost certainly be fielded by ride-hailing services, considering the cost of self-driving technology as well as liability and maintenance issues (责任与维护问题). But driverless car ownership could increase as the prices drop and more people become comfortable with the technology.</p>";
        TextView textView = mRoot.findViewById(R.id.text_view);
        textView.setText(Html.fromHtml(text));
    }

    @Override
    protected int onCreateContent() {
        return R.layout.activity_abc;
    }
}
