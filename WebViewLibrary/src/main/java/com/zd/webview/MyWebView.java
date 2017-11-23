package com.zd.webview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.ZoomButtonsController;

import java.lang.reflect.Method;

/**
 * Created by zhudi on 2017/11/22.
 */

public class MyWebView extends WebView {
    /**
     * 加载完成
     */
    public interface WebViewErrorListener {
        void error(MyWebView webView);
    }

    private ProgressBar progressBar;

    private WebViewErrorListener errorListener;

    public void setWebViewErrorListener(WebViewErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    public MyWebView(Context context) {
        super(context);
        initView();
        initializeOptions(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initializeOptions(context);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initializeOptions(context);
    }

    private void initView() {
//        addJavascriptInterface(new ContactPlugin(), "Android"); // JS通过Interface调用Java
        setWebViewClient(new WebViewClient() {
            // url拦截
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
                if (progressBar != null) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                view.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            // 页面加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
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
                showErrorUrl((MyWebView) view);

            }

            // 新版本，只会在Android6及以上调用
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (request.isForMainFrame()) { // 或者： if(request.getUrl().toString() .equals(getUrl()))
                    // 在这里显示自定义错误页
                    showErrorUrl((MyWebView) view);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                // 在这里显示自定义错误页
                if (!request.getUrl().toString().endsWith(".ico")) {
                    showErrorUrl((MyWebView) view);
                }
            }
        });

        setWebChromeClient(new WebChromeClient() {
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
                progressBar.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }

            // 设置程序的Title
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
    }

//    /**
//     * 界面回调
//     */
//    class ContactPlugin {
//        @JavascriptInterface
//        public void jsCallJava(String arg) {
//        }
//    }

    @SuppressLint({"NewApi"})
    public void initializeOptions(Context context) {
        WebSettings settings = this.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSaveFormData(true);
        settings.setSavePassword(true);
        settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        if (Build.VERSION.SDK_INT >= 8) {
            settings.setPluginState(WebSettings.PluginState.ON);
        }

        settings.setSupportZoom(true);
        if (Build.VERSION.SDK_INT >= 11) {
            settings.setBuiltInZoomControls(true);
            settings.setDisplayZoomControls(false);
        } else {
            this.getControlls();
        }

        settings.setSupportMultipleWindows(true);
        this.setLongClickable(true);
        this.setScrollbarFadingEnabled(true);
        //this.setScrollBarStyle(View.OVER_SCROLL_NEVER);
        this.setDrawingCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);

        getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);  //设置 缓存模式
        // 开启 DOM storage API 功能
        getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        getSettings().setDatabaseEnabled(true);
//        String cacheDirPath = getFilesDir().getAbsolutePath()+APP_CACAHE_DIRNAME;
//        String cacheDirPath = context.getCacheDir().getPath();
//        Log.i(TAG, "cacheDirPath=" + cacheDirPath);
        //设置数据库缓存路径
//        getSettings().setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
//        getSettings().setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        getSettings().setAppCacheEnabled(true);
    }

    private void getControlls() {
        try {
            Class e = Class.forName("android.webkit.WebView");
            Method method = e.getMethod("getZoomButtonsController", new Class[0]);
            ZoomButtonsController var3 = (ZoomButtonsController) method.invoke(this, new Object[]{Boolean.valueOf(true)});
        } catch (Exception var4) {
        }
    }

    private void showErrorUrl(MyWebView view) {
        if (errorListener != null) {
            errorListener.error(view);
        }
    }

    /**
     * 设置网页加载进度条
     *
     * @param progressBar
     */
    public void setProgressBarView(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

}
