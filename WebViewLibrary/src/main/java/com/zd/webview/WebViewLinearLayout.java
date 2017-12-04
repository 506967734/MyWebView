package com.zd.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

/**
 * Created by zhudi on 2017/11/22.
 */

public class WebViewLinearLayout extends RelativeLayout {
    private String url;
    private LinearLayout mContainer;
    private LinearLayout lilyError;
    private MyWebView wv;
    private ProgressBar progressBar;

    public WebViewLinearLayout(Context context) {
        super(context);
        initView(context);
    }

    public WebViewLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public WebViewLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.webview_linearlayout, null);
        addView(mContainer, lp);

        wv = (MyWebView) findViewById(R.id.wv);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        /**
         * 加载异常
         */
//        wv.setWebViewListener(new MyWebView.WebViewListener() {
//            @Override
//            public void error(MyWebView webview) {
//                webview.loadUrl("about:blank");
//                lilyError.setVisibility(View.VISIBLE);
//                webview.setVisibility(View.GONE);
//            }
//        });

        wv.setWebViewListener(new MyWebView.WebViewListener() {
            @Override
            public void error(MyWebView webView) {
                webView.loadUrl("about:blank");
                lilyError.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
            }

            @Override
            public void updata(int progress) {
                progressBar.setProgress(progress);
                if (progress < BrowserUnit.PROGRESS_MAX) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        lilyError = (LinearLayout) findViewById(R.id.lilyError);
        /**
         * 点击刷新
         */
        lilyError.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                lilyError.setVisibility(View.GONE);
                wv.setVisibility(View.VISIBLE);
                wv.loadUrl(url);
            }
        });

        /**
         * 设置Webiew的进度条
         */
        //wv.setProgressBarView((ProgressBar) findViewById(R.id.progressBar));
    }

    /**
     * 显示url界面
     *
     * @param url
     */
    public void loadUrl(String url) {
        if (wv != null) {
            this.url = url;
            wv.loadUrl(url);
        }
    }

    /**
     * destroyWebView
     */
    public void destroyWebView() {
        if (wv != null) {
            wv.clearHistory();
            wv.destroy();
            wv = null;
        }
    }

    /**
     * 后退
     */
    public void webViewGoBack() {
        if (webViewCanBack()) {
            wv.goBack();
        }
    }

    /**
     * webview是否还可以后退
     *
     * @return
     */
    public boolean webViewCanBack() {
        return (wv != null && wv.canGoBack());
    }
}
