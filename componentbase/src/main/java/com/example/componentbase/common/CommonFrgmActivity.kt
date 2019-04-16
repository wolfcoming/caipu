package com.example.componentbase.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DialogTitle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.componentbase.R
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.baselibrary.base.BaseActiviy
import com.infoholdcity.baselibrary.config.ARouterConfig
import java.lang.Exception

/**
 * Author：yangqing
 * Time：2019/4/16 9:55
 * Description：
 */

class CommonFrgmActivity : BaseActiviy() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_fragment)
        getData()
    }

    companion object {
        fun startCommonFrgmActivity(context: Context, title: String = "无标题", fragment: String, param: Bundle? = null) {
            val intent: Intent = Intent(context, CommonFrgmActivity::class.java)
            intent.putExtra("paramBundle", param)
            intent.putExtra("fragment", fragment)
            intent.putExtra("title", title)
            context.startActivity(intent)
        }
    }

    private fun getData() {
        val bundle = intent.extras
        val title = bundle.getString("title")
        val frgmName = bundle.getString("fragment")
        val param = bundle.getBundle("paramBundle")
        try {
            val frgm = Class.forName(frgmName).newInstance() as Fragment
            if (param != null) {
                frgm.arguments = param
            }

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_container, frgm)
            transaction.commit()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}