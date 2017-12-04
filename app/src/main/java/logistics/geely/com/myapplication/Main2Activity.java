package logistics.geely.com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.zd.webview.BrowserUnit;
import com.zd.webview.MyWebView;

public class Main2Activity extends AppCompatActivity {

    private LinearLayout lily;
    private MyWebView webView;

    private ProgressBar progressBar;
    private LinearLayout lilyError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        lily = (LinearLayout) findViewById(R.id.lily);
        lilyError = (LinearLayout) findViewById(R.id.lilyError);

//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//        LinearLayout mContainer = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_main, null);
//
//        webViewRelativeLayout = (WebViewLinearLayout) mContainer.findViewById(R.id.webViewLayout);
//        webViewRelativeLayout.loadUrl("https://www.baidu.com/");
//        lily.addView(mContainer, lp);

        progressBar = (ProgressBar) findViewById(com.zd.webview.R.id.progressBar);

        webView = new MyWebView(this);
        webView.setWebViewListener(new MyWebView.WebViewListener() {
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

        /**
         * 点击刷新
         */
        lilyError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lilyError.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                webView.loadUrl("http://www.baidu.com");
            }
        });
        webView.loadUrl("http://ww.baidu.cm");
        lily.addView(webView);
    }
}
