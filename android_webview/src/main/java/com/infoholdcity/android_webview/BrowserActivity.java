package com.infoholdcity.android_webview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.utils.TbsLog;

public class BrowserActivity extends Activity {
    /**
     * 作为一个浏览器的示例展示出来，采用android+web的模式
     */
    private X5WebView mWebView;
    private ViewGroup mViewParent;
    private ImageButton mBack;
    private ImageButton mForward;
    private ImageButton mExit;
    private ImageButton mHome;
    private ImageButton mMore;

    private static String mHomeUrl = "http://app.html5.qq.com/navi/index";
    private static final String TAG = "SdkDemo";
    private final int disable = 120;
    private final int enable = 255;
    private ValueCallback<Uri> uploadFile;
    private ProgressBar progressBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
            getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }
        setContentView(R.layout.activity_browser);
        mViewParent = (ViewGroup) findViewById(R.id.webView1);
        initBtnListenser();
        mHomeUrl = getIntent().getStringExtra("url");
        init();
    }

    private void changGoForwardButton(WebView view) {
        if (view.canGoBack())
            mBack.setAlpha(enable);
        else
            mBack.setAlpha(disable);
        if (view.canGoForward())
            mForward.setAlpha(enable);
        else
            mForward.setAlpha(disable);
        if (view.getUrl() != null && view.getUrl().equalsIgnoreCase(mHomeUrl)) {
            mHome.setAlpha(disable);
            mHome.setEnabled(false);
        } else {
            mHome.setAlpha(enable);
            mHome.setEnabled(true);
        }
    }


    private void init() {
        mWebView = new X5WebView(this, null);
        mViewParent.addView(mWebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        mWebView.setWebViewClient(new WebViewClient() {
            private String startUrl;

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("DDDDDD", "shouldOverrideUrlLoading: " + url);
                if (startUrl != null && startUrl.equals(url)) {
                    view.loadUrl(url);
                } else {
                    //交给系统处理
                    return super.shouldOverrideUrlLoading(view, url);
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar1.setVisibility(View.GONE);
                if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 16)
                    changGoForwardButton(view);
            }


            @Override
            public void onPageStarted(WebView webView, String url, Bitmap bitmap) {
                super.onPageStarted(webView, url, bitmap);
                progressBar1.setVisibility(View.VISIBLE);
                startUrl = url;
                Log.e("DDDDDD", "onPageStarted: " + url);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView webView, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView newWebView = new WebView(webView.getContext());
                newWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        // 在此处进行跳转URL的处理, 一般情况下_black需要重新打开一个页面, 这里我直接让当前的webview重新load了url
                        Log.e("DDDDDD222", "shouldOverrideUrlLoading: " + url);
                        if (url.startsWith("http")) {
                            mWebView.loadUrl(url);
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();
                return true;
            }


            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                progressBar1.setProgress(i);
            }
        });
        long time = System.currentTimeMillis();

        mWebView.loadUrl(mHomeUrl);

        TbsLog.d("time-cost", "cost time: "
                + (System.currentTimeMillis() - time));
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
    }

    private void initBtnListenser() {
        mBack = (ImageButton) findViewById(R.id.btnBack1);
        mForward = (ImageButton) findViewById(R.id.btnForward1);
        mExit = (ImageButton) findViewById(R.id.btnExit1);
        mHome = (ImageButton) findViewById(R.id.btnHome1);
        mMore = (ImageButton) findViewById(R.id.btnMore);
        progressBar1 = findViewById(R.id.progressBar1);
        if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 16) {
            mBack.setAlpha(disable);
            mForward.setAlpha(disable);
            mHome.setAlpha(disable);
        }
        mHome.setEnabled(false);

        mBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mWebView != null && mWebView.canGoBack())
                    mWebView.goBack();
            }
        });

        mForward.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mWebView != null && mWebView.canGoForward())
                    mWebView.goForward();
            }
        });


        mMore.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(BrowserActivity.this, "not completed",
                        Toast.LENGTH_LONG).show();
            }
        });

        mHome.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mWebView != null)
                    mWebView.loadUrl(mHomeUrl);
            }
        });

        mExit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
                if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 16)
                    changGoForwardButton(mWebView);
                return true;
            } else
                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TbsLog.d(TAG, "onActivityResult, requestCode:" + requestCode
                + ",resultCode:" + resultCode);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (null != uploadFile) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }

        }

    }

    @Override
    protected void onDestroy() {
        if (mWebView != null)
            mWebView.destroy();
        super.onDestroy();
    }


}
