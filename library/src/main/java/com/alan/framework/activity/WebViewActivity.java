package com.alan.framework.activity;

import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.LinearLayout;


import com.alan.common.AndroidTools;
import com.alan.framework.R;
import com.alan.framework.view.CommonTitleBar;


public class WebViewActivity extends StateBaseActivity {

    protected WebView mWebView;
    protected String mTitle = "";
    protected CommonTitleBar titleBar;
    private String mUrl;
    // 0 url 1 assets
    private int type;

    protected void load() {
        if (type == 1) {
            loadAssets();
        } else if (type == 0) {
            loadUrl();
        }

    }

    protected void loadAssets() {
        WebView webView = findViewById(R.id.web_view);
        webView.loadUrl("file:///android_asset/" + mUrl);
    }

    protected void loadUrl() {
        if (!AndroidTools.isNetworkAvailable(this)) {
            showFailureState("请连接网络后，点击重试", true);
            return;
        } else {
            mWebView.loadUrl(mUrl);
        }
        showSuccessState();
    }

    protected void initTitleBar(String title) {
        titleBar = findViewById(R.id.title_bar);
        titleBar.setTitle(title);
    }

    protected void initWebView() {
        mWebView = findViewById(R.id.web_view);
        mWebView.removeJavascriptInterface("searchBoxJavaBridge");
        mWebView.removeJavascriptInterface("accessibility");
        mWebView.removeJavascriptInterface("accessibilityTraversal");

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (TextUtils.isEmpty(mTitle) && titleBar != null)
                    titleBar.setTitle(title);
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                WebViewActivity.this.onProgressChanged(newProgress);
            }
        });

        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(false);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(false);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(false);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(false);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setSavePassword(false);
    }


    protected void onProgressChanged(int progress) {
    }


    @Override
    protected void initView() {
        initWebView();
        mUrl = getIntent().getStringExtra("url");
        mTitle = getIntent().getStringExtra("title");
        type = getIntent().getIntExtra("type", 0);
        initTitleBar(mTitle);
        load();
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            LinearLayout mContainer = findViewById(R.id.container);
            if (null != mContainer) {
                mContainer.removeView(mWebView);
            }
            mWebView.removeAllViews();
            mWebView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onRetryViewClickListener() {
        loadUrl();
    }
}
