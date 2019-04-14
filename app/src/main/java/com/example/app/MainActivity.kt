package com.example.app

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.example.componentbase.ServiceFactory
import com.example.componentbase.eventbus.SlideMenuEvent
import com.infoholdcity.baselibrary.base.BaseActiviy
import com.infoholdcity.baselibrary.utils.ColorUtils
import com.infoholdcity.baselibrary.utils.StatusBarHelper
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import com.yarolegovich.slidingrootnav.callback.DragListener
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : BaseActiviy() {
    private var slidingRootNav: SlidingRootNav? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSlidingNav(savedInstanceState)
        initBottomNavBar()

    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun openOrCloseSlideMenu(slideMenuEvent: SlideMenuEvent){
        if(slideMenuEvent.type==1){
            slidingRootNav?.openMenu()
        }else{
            slidingRootNav?.closeMenu()
        }
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
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
            val color = ColorUtils.getColor(progress, "#00574B", "#FFFFFF")
            changeStateBarColor(Color.parseColor(color))
        }
    }


    private fun initBottomNavBar() {

        val minFragment = ServiceFactory.instance.getUsercenterService()
            .getMineFragment(null, "")

        val categoryFragment = ServiceFactory.instance.getCaipuService()
            .getCategoryFragment(null, "")


        val shopFragment = ServiceFactory.instance.getShopService().getShopHomeFragment(null, "");

        val caipuHome = ServiceFactory.instance.getCaipuService().getCaipuHomeFragment(null,"caipuHome")


        val fragments = ArrayList<Fragment>()
        fragments.add(caipuHome)
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
