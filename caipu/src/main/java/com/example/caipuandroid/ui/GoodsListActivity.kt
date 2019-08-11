package com.example.caipuandroid.ui

import android.Manifest
import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.text.method.KeyListener
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.baidu.aip.asrwakeup3.core.inputstream.InFileStream
import com.baidu.aip.asrwakeup3.core.mini.AutoCheck
import com.baidu.aip.asrwakeup3.core.recog.MyRecognizer
import com.baidu.aip.asrwakeup3.core.recog.listener.ChainRecogListener
import com.baidu.aip.asrwakeup3.core.recog.listener.MessageStatusRecogListener
import com.baidu.aip.asrwakeup3.core.ui.BaiduASRDigitalDialog
import com.baidu.aip.asrwakeup3.core.ui.DigitalDialogInput
import com.baidu.aip.asrwakeup3.core.util.FileUtil
import com.baidu.aip.asrwakeup3.core.util.MyLogger
import com.baidu.speech.asr.SpeechConstant
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.animation.BaseAnimation
import com.example.caipuandroid.R
import com.example.caipuandroid.service.impl.IServiceNetImpl
import com.example.caipuandroid.ui.adapter.GoodsListAdapter
import com.example.caipuandroid.ui.vo.Greens
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.excute
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseActiviy
import com.infoholdcity.baselibrary.config.ARouterConfig
import com.infoholdcity.baselibrary.config.ARouterConfig.Companion.ACT_CAIPU_LIST
import com.infoholdcity.baselibrary.utils.FileUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_goodslist.*
import org.devio.takephoto.uitl.TFileUtils
import org.json.JSONObject
import java.lang.Exception
import java.lang.ref.SoftReference

@Route(path = ACT_CAIPU_LIST)
class GoodsListActivity : BaseActiviy() {
    companion object {
        val handler = Handler()
    }

    val service by lazy { IServiceNetImpl() }
    val adapter = GoodsListAdapter()
    var pageSize = 15
    var pageNumber = 1
    var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InFileStream.setContext(SoftReference(this))
        setContentView(R.layout.activity_goodslist)

        initYuyin()
        etName.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                showLoadingStatus()
                name = etName.text.toString()
                getData(true)
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }

        name = intent.getStringExtra("name")
        val showKeyBoard = intent.getBooleanExtra("showKeyBoard", false)
        etName.setText(name)

        if (!showKeyBoard) {
            etName.setSelection(name.length)
            etName.clearFocus()
        } else {
            etName.isFocusable = true
            etName.isFocusableInTouchMode = true
            etName.requestFocus()

        }

        rvGoodsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvGoodsList.adapter = adapter

        //在refreshLayout 界面显示不同状态界面
        initMuiltStatusArea(refreshLayout)
        showLoadingStatus()
        getData(true)
        refreshLayout.setEnableAutoLoadMore(false)
        refreshLayout.setOnLoadMoreListener { refreshLayout ->
            getData(false)
        }

        refreshLayout.setOnRefreshListener {
            getData(true)
        }
        tvSearch.setOnClickListener {
            showLoadingStatus()
            name = etName.text.toString()
            getData(true)
        }
        ivBack.setOnClickListener { finish() }


        adapter.setOnItemClickListener { adapter, view, position ->
            val greens = adapter.getItem(position) as Greens
            val bundle = Bundle()
            bundle.putSerializable("Greens", greens)
            ARouter.getInstance().build(ARouterConfig.ACT_CAIPU_DETAIL)
                .with(bundle)
                .navigation()
        }

        iv_audio.setOnClickListener {
            RxPermissions(this).request(Manifest.permission.RECORD_AUDIO)
                .subscribe {
                    if (it) {
                        showYuyin()
                    } else {
                        toast("请开启语音权限否则无法使用")
                    }
                }

        }

        adapter.openLoadAnimation()
        // 默认提供5种方法（渐显、缩放、从下到上，从左到右、从右到左）
        adapter.openLoadAnimation(object : BaseAnimation {
            override fun getAnimators(view: View?): Array<Animator> {
                return arrayOf(
                    ObjectAnimator.ofFloat(view, "alpha", 0f, 1f),
                    ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.2f, 1f),
                    ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.2f, 1f)
                )
            }

        })
        adapter.isFirstOnly(false)


    }


    protected var myRecognizer: MyRecognizer? = null
    /*
     * 本Activity中是否需要调用离线命令词功能。根据此参数，判断是否需要调用SDK的ASR_KWS_LOAD_ENGINE事件
     */
    protected var enableOffline: Boolean = false
    private var chainRecogListener: ChainRecogListener? = null
    private fun initYuyin() {

        val listener = MessageStatusRecogListener(handler)
        myRecognizer = MyRecognizer(this, listener)
        // DigitalDialogInput 输入 ，MessageStatusRecogListener可替换为用户自己业务逻辑的listener
        chainRecogListener = ChainRecogListener()
        chainRecogListener?.addListener(MessageStatusRecogListener(handler))
        myRecognizer?.setEventListener(chainRecogListener) // 替换掉原来的listener
    }

    private var input: DigitalDialogInput? = null
    private fun showYuyin() {
        // 此处params可以打印出来，直接写到你的代码里去，最终的json一致即可。
        val params: HashMap<String, Any> = HashMap()
        params.put("accept-audio-volume", false)

        // 复制此段可以自动检测常规错误
        AutoCheck(
            applicationContext, @SuppressLint("HandlerLeak")
            object : Handler() {
                override fun handleMessage(msg: Message) {
                    if (msg.what == 100) {
                        val autoCheck = msg.obj as AutoCheck
                        synchronized(autoCheck) {
                            val message = autoCheck.obtainErrorMessage() // autoCheck.obtainAllMessage()
                            Klog.e(contents = message)
                        }// message可以用下面一行替代，在logcat中查看代码
                    }
                }
            }, enableOffline
        ).checkAsr(params)
        input = DigitalDialogInput(myRecognizer, chainRecogListener, params)
        BaiduASRDigitalDialog.setInput(input) // 传递input信息，在BaiduASRDialog中读取,
        val intent = Intent(this, BaiduASRDigitalDialog::class.java)
        startActivityForResult(intent, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2) {
            var message = ""
            if (resultCode == RESULT_OK) {
                val results = data?.getStringArrayListExtra("results")
                if (results != null && results.size > 0) {
                    message += results[0]
                }
            }
            etName.setText(message)
            etName.setSelection(message.length)
        }
    }


    override fun onDestroy() {
        // 如果之前调用过myRecognizer.loadOfflineEngine()， release()里会自动调用释放离线资源
        // 基于DEMO5.1 卸载离线资源(离线时使用) release()方法中封装了卸载离线资源的过程
        // 基于DEMO的5.2 退出事件管理器
        myRecognizer?.cancel()
        myRecognizer?.release()
        super.onDestroy()


    }

    @SuppressLint("CheckResult")
    fun getData(isFresh: Boolean) {
        if (isFresh) {
            pageNumber = 1
        }
        service.getGreensList(pageSize, pageNumber, name)
            .excute()
            .subscribe({
                showLoadSuccessStatus()
                pageNumber++
                if (isFresh) {
                    refreshLayout.finishRefresh()
                    adapter.setNewData(it)
                    if (it.size == 0) {
                        showEmptyStatus()
                    }
                } else {
                    if (pageSize > it.size) {
                        refreshLayout.finishLoadMoreWithNoMoreData()
                    } else {
                        refreshLayout.finishLoadMore()
                    }
                    adapter.addData(it)
                }
            }, {
                refreshLayout.finishLoadMore()
                refreshLayout.finishRefresh()
                showLoadFailedStatus()
                toast(it.message!!)
            })
    }


    override fun onLoadRetry() {
        showLoadingStatus()
        getData(false)
    }


}