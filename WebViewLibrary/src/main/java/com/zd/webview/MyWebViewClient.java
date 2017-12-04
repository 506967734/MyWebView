package com.zd.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by zhudi on 2017/11/30.
 */

public class MyWebViewClient extends WebViewClient {
    private MyWebView webView;
    private Context context;

    public MyWebViewClient(Context context, MyWebView webView) {
        super();
        this.context = context;
        this.webView = webView;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
        view.loadUrl(url);
        // 相应完成返回true
        return true;
    }

    // 页面开始加载
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        view.setVisibility(View.VISIBLE);
        super.onPageStarted(view, url, favicon);
    }

    // 页面加载完成
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    // WebView加载的所有资源url
    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode,
                                String description, final String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return;
        }
        webView.showErrorUrl();
    }

    // 新版本，只会在Android6及以上调用
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (request.isForMainFrame()) { // 或者： if(request.getUrl().toString() .equals(getUrl()))
            // 在这里显示自定义错误页
            webView.showErrorUrl();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
        // 在这里显示自定义错误页
        if (!request.getUrl().toString().endsWith(".ico")) {
            webView.showErrorUrl();
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        webView.showSslError(handler);
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }
}
