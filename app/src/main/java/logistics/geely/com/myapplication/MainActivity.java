package logistics.geely.com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zd.webview.WebViewLinearLayout;


public class MainActivity extends AppCompatActivity {

    private WebViewLinearLayout webViewRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webViewRelativeLayout = (WebViewLinearLayout) findViewById(R.id.webViewLayout);
        webViewRelativeLayout.loadUrl("https://www.baidu.com/");
    }

    @Override
    protected void onDestroy() {
        webViewRelativeLayout.destroyWebView();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (webViewRelativeLayout.webViewCanBack()) {
            webViewRelativeLayout.webViewGoBack();
        } else {
            finish();
        }
    }
}
