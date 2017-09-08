package com.xytsz.xytsz.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.xytsz.xytsz.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/8/2.
 *
 * 展示页
 */
public class MemberShowContentActivity extends AppCompatActivity {

    @Bind(R.id.membershoucontent_webview)
    WebView membershoucontentWebview;
    @Bind(R.id.membershouwcontent_progressbar)
    ProgressBar membershouwcontentProgressbar;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membershowcontent);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("会员展示");

        }

        if (getIntent() != null){
            url = getIntent().getStringExtra("url");
        }


        //v-url : "http://10.0.2.2:8080/zhbj/10007/724D6A55496A11726628.html"
        membershoucontentWebview.loadUrl(url);
        WebSettings settings = membershoucontentWebview.getSettings();
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);


        membershoucontentWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //加载开始
                membershouwcontentProgressbar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //加载完成后

                membershouwcontentProgressbar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
