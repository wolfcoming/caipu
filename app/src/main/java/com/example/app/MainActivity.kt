package com.example.app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.example.componentbase.ServiceFactory
import com.infoholdcity.baselibrary.config.ARouterConfig
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnCaipu.setOnClickListener {
            ARouter.getInstance().build(ARouterConfig.ACT_CAIPU_HOME).navigation()
        }

        btnShop.setOnClickListener {
            ARouter.getInstance().build(ARouterConfig.ACT_SHOP_HOME).navigation();
        }
        btnUser.setOnClickListener {
            ARouter.getInstance().build(ARouterConfig.ACT_USER_LOGIN).navigation();
        }


        val bundle = Bundle()
        bundle.putString("args", "app传递参数")

        ServiceFactory.instance.getUsercenterService()
            .getMinFragment(this, R.id.fl_contain, supportFragmentManager, bundle, "")

    }
}
