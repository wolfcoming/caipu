package com.infoholdcity.android_webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * @author yangqing
 * @time 2019-11-12 11:10
 * @describe
 */
public class AndroidWebViewActivity extends Activity {

    private WebView mWebView;
    private FrameLayout mWebViewContainer;
    private String mUrl;

    public static void showWebViewAcitvity(Activity context, String url) {
        Intent intent = new Intent(context, AndroidWebViewActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initview();
        getData();
    }


    private void close() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        }
    }

    @Override
    public void onBackPressed() {
        close();
    }

    private void initview() {
        mWebViewContainer = findViewById(R.id.fl_webview_container);
        initWebview();
    }

    private void initWebview() {
        WeakReference<Context> mWeakContext = new WeakReference(this);
        mWebView = new WebView(mWeakContext.get());
        mWebViewContainer.addView(mWebView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        setDefaultWebSettings(mWebView);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = "";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    url = request.getUrl().toString();
                } else {
                    url = request.toString();
                }
                if (!request.toString().startsWith("http")) {
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient());
        removeJavascriptInterfaces(mWebView);
        mWebView.addJavascriptInterface(new JSInteration(), "androidHander");

    }

    private void getData() {
        //展示webview
        mUrl = getIntent().getStringExtra("url");
        mWebView.loadUrl(mUrl);


    }


    public void setDefaultWebSettings(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        //5.0以上开启混合模式加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //允许js代码
        webSettings.setJavaScriptEnabled(true);
        //允许SessionStorage/LocalStorage存储
        webSettings.setDomStorageEnabled(false);
        //禁用放缩
        webSettings.setDisplayZoomControls(false);
        webSettings.setBuiltInZoomControls(false);
        //禁用文字缩放
        webSettings.setTextZoom(100);
        webSettings.setAllowFileAccess(false);
        webSettings.setSavePassword(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(false);
        }
    }

    /**
     * 解决api17之下的安全问题
     *
     * @param webView
     */
    private static final void removeJavascriptInterfaces(WebView webView) {
        try {
            if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 17) {
                webView.removeJavascriptInterface("searchBoxJavaBridge_");
                webView.removeJavascriptInterface("accessibility");
                webView.removeJavascriptInterface("accessibilityTraversal");
            }
        } catch (Throwable tr) {
            tr.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebViewContainer != null) {
            mWebViewContainer.removeView(mWebView);
        }
        mWebView.stopLoading();
        // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
        mWebView.getSettings().setJavaScriptEnabled(false);
        mWebView.clearHistory();
        mWebView.clearView();
        mWebView.removeAllViews();
        mWebView.destroy();
        mWebView = null;
    }


    class JSInteration {



        @JavascriptInterface
        public void calledByJs(String param,String callback) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AndroidWebViewActivity.this, param, Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mWebView.evaluateJavascript(callback, value -> Log.e("YYYYY", "onReceiveValue: " + value));
                        }
                    },3000);
                }
            });
        }

    }


}
