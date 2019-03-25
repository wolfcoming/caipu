package com.example.app

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.componentbase.ServiceFactory
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.config.ARouterConfig
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initBottomNavBar()

    }

    private fun initBottomNavBar() {

        val minFragment = ServiceFactory.instance.getUsercenterService()
            .getMineFragment(null, "")

        val categoryFragment = ServiceFactory.instance.getCaipuService()
            .getCategoryFragment(null,"")

        val fragments = ArrayList<Fragment>()
        fragments.add(TempFragment())
        fragments.add(categoryFragment)
        fragments.add(TempFragment())
        fragments.add(minFragment!!)

        val beginTransaction = supportFragmentManager.beginTransaction()
        fragments.map {
            beginTransaction.add(R.id.hom_coantiner, it).hide(it)
        }
        beginTransaction.show(fragments[0])
        beginTransaction.commit()


        bottomNavBar.setMode(BottomNavigationBar.MODE_FIXED)
            .setBarBackgroundColor("#FFFFFF") // 背景颜色
            .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
            .setInActiveColor("#000000") // 未选中状态颜色
            .setActiveColor("#FF0000") // 选中状态颜色
            .addItem(
                BottomNavigationItem(
                    R.mipmap.ic_launcher,
                    "首页"
                ).setInactiveIconResource(R.mipmap.shouye)
            )
            .addItem(
                BottomNavigationItem(
                    R.mipmap.ic_launcher,
                    "消息"
                ).setInactiveIconResource(R.mipmap.ic_launcher)
            )
            .addItem(
                BottomNavigationItem(
                    R.mipmap.ic_launcher,
                    "我的"
                ).setInactiveIconResource(R.mipmap.ic_launcher)
            )
            .addItem(
                BottomNavigationItem(
                    R.mipmap.ic_launcher,
                    "其他"
                ).setInactiveIconResource(R.mipmap.ic_launcher)
            )
            .setFirstSelectedPosition(0) //设置默认选中位置
            .initialise()  // 提交初始化（完成配置）


        bottomNavBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {
            }

            override fun onTabUnselected(position: Int) {
            }

            override fun onTabSelected(position: Int) {
                val beginTransaction1 = supportFragmentManager.beginTransaction()
                fragments.map {
                    beginTransaction1.hide(it)
                }
                beginTransaction1.show(fragments[position])
                    .commit()

            }
        })


    }

}
