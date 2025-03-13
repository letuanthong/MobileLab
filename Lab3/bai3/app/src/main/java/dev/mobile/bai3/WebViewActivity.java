package dev.mobile.bai3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        WebView webView = findViewById(R.id.webView);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("url")) {
            String url = intent.getStringExtra("url");
            assert url != null;
            webView.loadUrl(url);
        }
    }
}
