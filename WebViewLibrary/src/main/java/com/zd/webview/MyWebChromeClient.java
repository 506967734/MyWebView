package com.zd.webview;

import android.content.Context;
import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by apple on 2017/11/30.
 */

public class MyWebChromeClient extends WebChromeClient {
    private MyWebView webView;
    private Context context;

    public MyWebChromeClient(Context context, MyWebView webView) {
        super();
        this.context = context;
        this.webView = webView;
        Log.e("MyWebChromeClient", "MyWebChromeClient---->MyWebChromeClient---->");
    }

    @Override
    // 处理javascript中的alert
    public boolean onJsAlert(WebView view, String url, String message,
                             final JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    // 处理javascript中的confirm
    public boolean onJsConfirm(WebView view, String url,
                               String message, final JsResult result) {
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    // 处理javascript中的prompt
    public boolean onJsPrompt(WebView view, String url, String message,
                              String defaultValue, final JsPromptResult result) {
        return super.onJsPrompt(view, url, message, defaultValue,
                result);
    }

    // 设置网页加载的进度条
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        webView.updateProgressBar(newProgress);
        super.onProgressChanged(view, newProgress);
    }

    // 设置程序的Title
    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
    }
}
