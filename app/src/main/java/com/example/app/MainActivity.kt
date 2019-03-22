package com.example.app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.componentbase.ServiceFactory
import com.infoholdcity.basearchitecture.self_extends.Klog
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



        rv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        val adapter = MyAdapter()
        rv.adapter = adapter
        val list = arrayListOf("1","2","3","4")
         adapter.setNewData(list)


        val bundle = Bundle()
        bundle.putString("args", "app传递参数")

        ServiceFactory.instance.getUsercenterService()
            .getMinFragment(this, R.id.fl_contain, supportFragmentManager, bundle, "")

    }

    class MyAdapter :BaseQuickAdapter<String,BaseViewHolder>{
        constructor():super(R.layout.item_test);
        override fun convert(helper: BaseViewHolder?, item: String?) {
            helper!!.getView<TextView>(R.id.btnTest).isClickable =true
            helper!!.getView<TextView>(R.id.btnTest).setOnClickListener {
                Toast.makeText(helper!!.itemView.context,"点击内容区域",Toast.LENGTH_SHORT).show()
            }
            helper!!.getView<TextView>(R.id.tvDelete).setOnClickListener {
                Toast.makeText(helper!!.itemView.context,"删",Toast.LENGTH_SHORT).show()
            }
        }

    }
}
