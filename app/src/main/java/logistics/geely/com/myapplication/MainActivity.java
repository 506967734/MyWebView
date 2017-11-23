package logistics.geely.com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import logistics.geely.com.myapplication.wedget.WebViewLinearLayout;

public class MainActivity extends AppCompatActivity {
    private WebViewLinearLayout webViewRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webViewRelativeLayout = (WebViewLinearLayout) findViewById(R.id.webViewLayout);
        webViewRelativeLayout.loadUrl("https://www.baidu.com");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webViewRelativeLayout.destroyWebView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (webViewRelativeLayout.webViewCanBack()) {
            webViewRelativeLayout.webViewGoBack();
        } else {
            finish();
        }
    }
}
