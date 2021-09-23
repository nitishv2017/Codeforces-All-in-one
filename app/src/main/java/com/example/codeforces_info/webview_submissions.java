package com.example.codeforces_info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class webview_submissions extends AppCompatActivity {
String link="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_submissions);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ImageView back=findViewById(R.id.back_web);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview_submissions.this.onBackPressed();
            }
        });

        Intent intent = getIntent();
        link = intent.getExtras().getString("link");
        // Find the WebView by its unique ID
        WebView w = (WebView) findViewById(R.id.web);

        // loading http://www.google.com url in the the WebView.
        w.loadUrl(link);

        // this will enable the javascipt.
        w.getSettings().setJavaScriptEnabled(true);
        w.getSettings().setLoadWithOverviewMode(true);
        w.getSettings().setUseWideViewPort(true);
        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        w.setWebViewClient(new WebViewClient());
    }
}