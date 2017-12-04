package com.zd.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;

import java.lang.reflect.Method;

/**
 * Created by zhudi on 2017/11/22.
 */

public class MyWebView extends WebView {
    /**
     * 加载完成
     */
    public interface WebViewListener {
        void error(MyWebView webView);

        void updata(int progress);
    }

    private WebViewListener webViewListener;
    private Context context;
    private MyWebViewClient webViewClient;
    private MyWebChromeClient webChromeClient;

    public void setWebViewListener(WebViewListener errorListener) {
        this.webViewListener = errorListener;
    }

    public MyWebView(Context context) {
        super(context);
        this.context = context;
        this.webViewClient = new MyWebViewClient(context, this);
        this.webChromeClient = new MyWebChromeClient(context, this);
        initView();
        initializeOptions(context);
    }


    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.webViewClient = new MyWebViewClient(context, this);
        this.webChromeClient = new MyWebChromeClient(context, this);
        initView();
        initializeOptions(context);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.webViewClient = new MyWebViewClient(context, this);
        this.webChromeClient = new MyWebChromeClient(context, this);
        initView();
        initializeOptions(context);
    }


    private void initView() {
        setWebViewClient(webViewClient);
        setWebChromeClient(webChromeClient);
//        addJavascriptInterface(new ContactPlugin(), "Android"); // JS通过Interface调用Java
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
        if (webViewListener != null) {
            webViewListener.error(view);
        }
    }


    @Override
    public void loadUrl(String url) {
        super.loadUrl(url);
    }


    public synchronized void updateProgressBar(int newProgress) {
        webViewListener.updata(newProgress);
    }

    public synchronized void showErrorUrl() {
        this.showErrorUrl(this);
    }

    public synchronized void showSslError(SslErrorHandler handler) {
        handler.proceed();
    }

    /**
     * destroyWebView
     */
    public void destroyWebView() {
        clearHistory();
        destroy();
    }
}
