package com.example.caipuandroid.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.baidu.aip.asrwakeup3.core.inputstream.InFileStream
import com.baidu.aip.asrwakeup3.core.mini.AutoCheck
import com.baidu.aip.asrwakeup3.core.recog.MyRecognizer
import com.baidu.aip.asrwakeup3.core.recog.listener.ChainRecogListener
import com.baidu.aip.asrwakeup3.core.recog.listener.MessageStatusRecogListener
import com.baidu.aip.asrwakeup3.core.util.FileUtil
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
import kotlinx.android.synthetic.main.activity_goodslist.*
import org.devio.takephoto.uitl.TFileUtils

@Route(path = ACT_CAIPU_LIST)
class GoodsListActivity : BaseActiviy() {
    val service by lazy { IServiceNetImpl() }
    val adapter = GoodsListAdapter()
    var pageSize = 15
    var pageNumber = 1
    var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InFileStream.setContext(this)
        setContentView(R.layout.activity_goodslist)

        initYuyin()

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

            showYuyin()
        }
    }


    val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Klog.e(contents = msg.obj.toString() + "\n")
            FileUtils.writeFileToSDCardAppend(msg.obj.toString(),"菜谱","yuyin.txt")


        }

    }
    protected var myRecognizer: MyRecognizer? = null
    /*
     * 本Activity中是否需要调用离线命令词功能。根据此参数，判断是否需要调用SDK的ASR_KWS_LOAD_ENGINE事件
     */
    protected var enableOffline: Boolean = false
    private var chainRecogListener: ChainRecogListener? = null
    private fun initYuyin() {

        // DEMO集成步骤 1.2 新建一个回调类，识别引擎会回调这个类告知重要状态和识别结果
        val listener = MessageStatusRecogListener(handler)
        // DEMO集成步骤 1.1 1.3 初始化：new一个IRecogListener示例 & new 一个 MyRecognizer 示例,并注册输出事件
        myRecognizer = MyRecognizer(this, listener)
//        if (enableOffline) {
//            // 基于DEMO集成1.4 加载离线资源步骤(离线时使用)。offlineParams是固定值，复制到您的代码里即可
//            val offlineParams = OfflineRecogParams.fetchOfflineParams()
//            myRecognizer?.loadOfflineEngine(offlineParams)
//        }


//        /**
//         * 有2个listner，一个是用户自己的业务逻辑，如MessageStatusRecogListener。另一个是UI对话框的。
//         * 使用这个ChainRecogListener把两个listener和并在一起
//         */
//        chainRecogListener = ChainRecogListener()
//        // DigitalDialogInput 输入 ，MessageStatusRecogListener可替换为用户自己业务逻辑的listener
//        chainRecogListener?.addListener(MessageStatusRecogListener(handler))
//        myRecognizer?.setEventListener(chainRecogListener) // 替换掉原来的listener
    }


    private fun showYuyin() {
        toast("hahha")

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

        // DEMO集成步骤2.2 开始识别
        myRecognizer?.start(params)
    }

    protected fun stop() {
        myRecognizer?.stop()
    }

    /**
     * 开始录音后，手动点击“取消”按钮。
     * SDK会取消本次识别，回到原始状态。
     * 基于DEMO集成4.2 发送取消事件 取消本次识别
     */
    protected fun cancel() {
        myRecognizer?.cancel()
    }

    override fun onDestroy() {
        // 如果之前调用过myRecognizer.loadOfflineEngine()， release()里会自动调用释放离线资源
        // 基于DEMO5.1 卸载离线资源(离线时使用) release()方法中封装了卸载离线资源的过程
        // 基于DEMO的5.2 退出事件管理器
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