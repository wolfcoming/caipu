package com.infoholdcity.android_webview

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.widget.FrameLayout
import android.widget.Toast
import com.tencent.smtt.export.external.interfaces.ConsoleMessage
import com.tencent.smtt.export.external.interfaces.JsResult
import com.tencent.smtt.sdk.*
import kotlinx.android.synthetic.main.activity_webview_main.*
import android.os.Vibrator
import android.widget.EditText
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.listener.ShakeListener
import com.tbruyelle.rxpermissions2.RxPermissions
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.uuzuche.lib_zxing.activity.CaptureActivity
import com.uuzuche.lib_zxing.activity.CodeUtils


class MainActivity : AppCompatActivity() {
    private var mWebView: X5WebView? = null
    private var mViewParent: ViewGroup? = null
    private val mHomeUrl = "file:///android_asset/dist2/index.html";
    private var mHomeUrl2 = "http://192.168.43.224:8080/#/pageOne";
    private var mErrorUrl = "http://192.168.1.112:8080/#/errorpage";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFormat(PixelFormat.TRANSLUCENT)
        if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
            window.setFlags(android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)
        }
        setContentView(R.layout.activity_webview_main)
        mViewParent = findViewById<View>(R.id.webView1) as ViewGroup
        initWebview()

        btnReload.setOnClickListener {
            reload()
//            RxPermissions(this)
//                    .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    .subscribe { granted ->
//                        if (granted) {
//                            val intent = Intent(this@MainActivity, CaptureActivity::class.java)
//                            startActivityForResult(intent, 1000)
//                        } else {
//                            toast("获取权限失败")
//                        }
//                    }

        }




        initListener()

        val shakeListener = ShakeListener(this)//创建一个对象
        shakeListener.setOnShakeListener {
            //对手机摇晃后的处理
            onVibrator();
        }
    }


    var dialog: AlertDialog? = null
    @SuppressLint("WrongConstant")
    private fun onVibrator() {
        if (dialog == null) {
            var vibrator: Vibrator? = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (vibrator == null) {
                val localVibrator = getApplicationContext()
                        .getSystemService("vibrator") as Vibrator
                vibrator = localVibrator
            }
            vibrator!!.vibrate(100L)

            val parm = ViewGroup.LayoutParams(-1, -2)
            val editText = EditText(this)
            editText.setText(mHomeUrl2)
            editText.layoutParams = parm
            dialog = AlertDialog.Builder(this).setView(editText)
                    .setTitle("请输入要展示的地址")
                    .setNegativeButton("确定") { dialog, which ->
                        mHomeUrl2 = editText.text.toString()
                        mWebView?.loadUrl(mHomeUrl2)
                    }
                    .show()
        } else {
            if (dialog?.isShowing!!) {
                return
            } else {
                dialog?.show()
            }
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        mViewParent?.removeAllViews()
    }

    private fun initWebview() {
        mWebView = X5WebView(this, null)
        mViewParent?.addView(mWebView, FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT))
        mWebView?.loadUrl(mHomeUrl2)
        progressBar.max = 100
        progressBar.visibility = View.VISIBLE
        mWebView?.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(p0: WebView?, p1: Int) {
                super.onProgressChanged(p0, p1)
                Klog.e(contents = "加载进度为：${p1}")
                progressBar.setProgress(p1)
                if (p1 == 100) {
                    progressBar.visibility = View.GONE
                }
            }

            override fun onJsAlert(p0: WebView?, p1: String?, p2: String?, result: JsResult?): Boolean {
                Klog.e(contents = "onJsAlert：${p1}")//p1打印的是地址  p2 打印的是参数
                var msg = ""
                if (p2 != null) {
                    msg = p2
                }
                AlertDialog.Builder(this@MainActivity)
                        .setTitle(msg)
                        .show()
//                需要调用confirm 和return true  才能多次调用 和 不显示vue默认alert
                result?.confirm()
                return true
            }


            override fun onConsoleMessage(mesage: ConsoleMessage?): Boolean {
                val msg = mesage?.message()
//                toast(msg!!)
                return super.onConsoleMessage(mesage)
            }


        }

        mWebView?.webViewClient = object : WebViewClient() {
            override fun onReceivedError(view: WebView,
                                         errorCode: Int, description:
                                         String, failingUrl: String) {
                Klog.e(contents = "onReceivedError")
                super.onReceivedError(view, errorCode, description, failingUrl)
                // 断网或者网络连接超时
                if (errorCode == ERROR_HOST_LOOKUP || errorCode == ERROR_CONNECT
                        || errorCode == ERROR_TIMEOUT) {
                    view.loadUrl("about:blank") // 避免出现默认的错误界面
                    view.loadUrl(mErrorUrl)
                }
            }

            override fun onPageFinished(p0: WebView?, p1: String?) {
                Log.e("QQQ", p1!!)
                super.onPageFinished(p0, p1)
            }
        }

        mWebView?.addJavascriptInterface(JsInteration(), "androidHandler")
    }

    inner class JsInteration {
        @JavascriptInterface
        fun calledByJs(url: String): String {
            //该方法所在的线程不是主线程 线程名为：JavaBridge
            Klog.e(contents = Thread.currentThread().name)
            mWebView?.post {
                toast("js传递过来的参数是：" + url)
                for (i in 0..10) {
                    SystemClock.sleep(100)
                    Klog.e(contents = i.toString())
                }
            }
            return "Android 已经接受到了你传递的url"
        }
    }


    fun reload() {
        progressBar.visibility = View.VISIBLE
        mWebView?.reload()
    }

    private fun initListener() {
        btnCallJsMethods.setOnClickListener {
            androidCallJs("1+1 等于多少")
        }
    }

    //Android 调用js方法
    fun androidCallJs(param: String) {
        val script = "calledByAndroid('$param')"
        mWebView?.evaluateJavascript(script, object : ValueCallback<String> {
            override fun onReceiveValue(p0: String) {
                toast("js返回Android结果是：${p0}")
            }
        })
    }


    override fun onBackPressed() {
        if (mWebView != null) {
            if (mWebView?.canGoBack()!!) {
                mWebView?.goBack()
            } else {
                finish()
            }
        } else {
            finish()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                val bundle = data.extras ?: return;
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    val result = bundle.getString(CodeUtils.RESULT_STRING);
                    mWebView!!.loadUrl(result)
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this@MainActivity, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}

