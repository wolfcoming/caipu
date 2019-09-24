package com.example.learncomponent.activity

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.os.*
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.learncomponent.R
import com.example.learncomponent.adapter.SimpleAdapter
import com.example.learncomponent.fresh.RefreshActivity
import com.infoholdcity.basearchitecture.self_extends.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        rvMain.layoutManager = LinearLayoutManager(this)
        val simpleAdapter = SimpleAdapter()
        rvMain.adapter = simpleAdapter
        var datas = LinkedHashMap<String, Class<*>>()
        datas.put("刷新控件", RefreshActivity::class.java)
        datas.put("滑动嵌套_悬停布局", NestActivity::class.java)
        datas.put("仿微博视频列表拖动播放", VideoListAutoPlayActivity::class.java)
        datas.put("自定义LayoutManage", LayoutmanageActivity::class.java)
        datas.put("应用升级安装", TestActivity::class.java)


        val lists = ArrayList<String>()
        for (data in datas) {
            lists.add(data.key)
        }

        simpleAdapter.setNewData(lists)

        simpleAdapter.setOnItemClickListener { adapter, view, position ->
            if (position == 0) {
                val canFingerprint = canFingerprint()
                toast(canFingerprint.toString())
            } else {
                startActivity(Intent(this, datas[lists[position]]))
            }
        }

    }


    /**
     * 检测手机是否有指纹识别硬件
     *
     * @return errorcode
     */
    fun canFingerprint(): Int {
        var fingerprintManager: FingerprintManagerCompat? = null
        // 判断手机是否是Android6.0以上
        if (android.os.Build.VERSION.SDK_INT < 23) {
            return 4
        }

        try {
            if (fingerprintManager == null) {
                fingerprintManager = FingerprintManagerCompat.from(this)
            }

            if (fingerprintManager == null) {
                Log.e("YYYYYY", "canFingerprint: == null")
                return 1
            }

            // 检测手机是否有指纹识别硬件
            if (!fingerprintManager.isHardwareDetected()) {
                Log.e("YYYYYY", "canFingerprint: 不支持")
                return 1
            }

            // 检测手机是否设置锁屏密码
            if (!this.isKeyguardSecure()) {
                return 2
            }

            // 检测手机是否有指纹
            return if (!fingerprintManager.hasEnrolledFingerprints()) {
                3
            } else 0

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("YYYYYY", "canFingerprint: 报错了" + e.message)
            return 1
        }
    }

    /**
     * 检测手机是否设置锁屏密码
     *
     * @return
     */
    private fun isKeyguardSecure(): Boolean {
        val keyguardManager = this
            .getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (!keyguardManager.isKeyguardSecure) {
                return false
            }
        }
        return true
    }


}
