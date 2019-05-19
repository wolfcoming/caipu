package com.example.app

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.example.componentbase.ServiceFactory
import com.example.componentbase.eventbus.SlideMenuEvent
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseActiviy
import com.infoholdcity.baselibrary.utils.ColorUtils
import com.infoholdcity.baselibrary.utils.StatusBarHelper
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import com.yarolegovich.slidingrootnav.callback.DragListener
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : BaseActiviy() {
    private var slidingRootNav: SlidingRootNav? = null
    private var minFragment: Fragment? = null
    private var categoryFragment: Fragment? = null
    private var shopFragment: Fragment? = null
    private var caipuHome: Fragment? = null
    private var tempFragment: Fragment? = null
    val fragments = ArrayList<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarHelper.setStatusBar(this@MainActivity, false, false)
        initSlidingNav(savedInstanceState)
        if (savedInstanceState != null) {
            isonSaveInstanceState = true
            caipuHome = supportFragmentManager.findFragmentByTag("CaipuHome")
            categoryFragment = supportFragmentManager.findFragmentByTag("CategoryFragment")
            tempFragment = supportFragmentManager.findFragmentByTag("TempFragment")
            shopFragment = supportFragmentManager.findFragmentByTag("ShopFragment")
            minFragment = supportFragmentManager.findFragmentByTag("MinFragment")

        }
        initBottomNavBar()

        permissions()
    }

    @SuppressLint("CheckResult")
    private fun permissions() {
        RxPermissions(this).request(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ).subscribe {
            if(it){

            }else{
                toast("未授权权限，部分功能不能使用")
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        MyTask().execute()

    }

    class MyTask : AsyncTask<Void, Int, String>() {
        override fun doInBackground(vararg params: Void?): String {
            return "reult"
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun openOrCloseSlideMenu(slideMenuEvent: SlideMenuEvent) {
        if (slideMenuEvent.type == 1) {
            slidingRootNav?.openMenu()
        } else {
            slidingRootNav?.closeMenu()
        }
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
        EventBus.getDefault().post(SlideMenuEvent(1))
    }

    private fun initSlidingNav(savedInstanceState: Bundle?) {

        //        好处：入侵 不需要处理布局 缺点：逻辑需要在MainActivity处理
        slidingRootNav = SlidingRootNavBuilder(this)
//            .withToolbarMenuToggle(toolbar)
            .withMenuOpened(false)
            .withContentClickableWhenMenuOpened(true)
            .withSavedState(savedInstanceState)
            .withMenuLayout(R.layout.menu_left_drawer)
            .addDragListener(myDragListener())
            .inject()


    }

    inner class myDragListener : DragListener {
        override fun onDrag(progress: Float) {
            //动态改变状态栏颜色
            val color = ColorUtils.getColor(progress, "#FFFFFF", "#FFFFFF")
            changeStateBarColor(Color.parseColor(color))
        }
    }

    var isonSaveInstanceState = false
    private fun initBottomNavBar() {

        if (caipuHome == null) {
            caipuHome = ServiceFactory.instance.getCaipuService().getCaipuHomeFragment(null, "caipuHome")
        }



        if (categoryFragment == null) {
            categoryFragment = ServiceFactory.instance.getCaipuService()
                .getCategoryFragment(null, "")

        }

        if (shopFragment == null) {
            shopFragment = ServiceFactory.instance.getShopService().getShopHomeFragment(null, "");
        }

        if (tempFragment == null) {
            tempFragment = TempFragment()

        }

        if (minFragment == null) {
            minFragment = ServiceFactory.instance.getUsercenterService()
                .getMineFragment(null, "")
        }

        fragments.add(caipuHome!!)
        fragments.add(categoryFragment!!)
        fragments.add(tempFragment!!)
        fragments.add(minFragment!!)


        if (!isonSaveInstanceState) {
            val beginTransaction = supportFragmentManager.beginTransaction()
            fragments.forEachIndexed { i, f ->
                var tag = ""
                when (i) {
                    0 -> tag = "CaipuHome"
                    1 -> tag = "CategoryFragment"
                    2 -> tag = "TempFragment"
                    3 -> tag = "MinFragment"
                }
                beginTransaction.add(R.id.hom_coantiner, f, tag).hide(f)
            }
            beginTransaction.show(fragments[0])
            beginTransaction.commit()
        }

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
