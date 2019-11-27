package com.alan.widget.corner;


import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/***
 * 设置圆角
 * @author wxw
 */
public class WTextView extends AppCompatTextView {

	public WViewHelper WViewHelper;
	public WTextView(Context context) {
		super(context);
		init(context, null);
	}

	public WTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public WTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs){
		WViewHelper = new WViewHelper();
		WViewHelper.init(context, attrs, this);
	}
}
