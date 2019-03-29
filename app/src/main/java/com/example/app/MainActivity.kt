package com.example.app

import android.os.Bundle
import android.support.v4.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.example.componentbase.ServiceFactory
import com.infoholdcity.baselibrary.base.BaseActiviy
import com.infoholdcity.baselibrary.utils.StatusBarHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActiviy() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNavBar()

    }

    private fun initBottomNavBar() {

        val minFragment = ServiceFactory.instance.getUsercenterService()
            .getMineFragment(null, "")

        val categoryFragment = ServiceFactory.instance.getCaipuService()
            .getCategoryFragment(null, "")


        val shopFragment = ServiceFactory.instance.getShopService().getShopHomeFragment(null, "");

        val fragments = ArrayList<Fragment>()
        fragments.add(shopFragment)
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
                    R.drawable.app_icon_home_press,
                    "首页"
                ).setInactiveIconResource(R.drawable.app_icon_home)
            )
            .addItem(
                BottomNavigationItem(
                    R.drawable.app_icon_category_press,
                    "分类"
                ).setInactiveIconResource(R.drawable.app_icon_category)
            )
            .addItem(
                BottomNavigationItem(
                    R.drawable.app_icon_chat_press,
                    "测试"
                ).setInactiveIconResource(R.drawable.app_icon_chat)
            )
            .addItem(
                BottomNavigationItem(
                    R.drawable.app_icon_person_press,
                    "我的"
                ).setInactiveIconResource(R.drawable.app_icon_person)
            )
            .setFirstSelectedPosition(0) //设置默认选中位置
            .initialise()  // 提交初始化（完成配置）


        bottomNavBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {
            }

            override fun onTabUnselected(position: Int) {
            }

            override fun onTabSelected(position: Int) {
                if (position == 0) {
                    StatusBarHelper.setStatusBar(this@MainActivity, false, true)
                } else {
                    StatusBarHelper.setStatusBar(this@MainActivity, false, false)
                }
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
