package com.infoholdcity.baselibrary.base

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.infoholdcity.baselibrary.utils.StatusBarHelper
import com.infoholdcity.baselibrary.view.muiltview.Gloading
import com.noober.background.BackgroundLibrary
import com.yq.wolfcoming.baselibrary.common.AppManage
import org.greenrobot.eventbus.EventBus

/**
 * Author：yangqing
 * Time：2018/12/11 16:46
 * Description：
 */
abstract class BaseActiviy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        BackgroundLibrary.inject(this)
        super.onCreate(savedInstanceState)
        //   解决共享元素闪屏问题
        getWindow().setBackgroundDrawableResource(android.R.color.transparent)
        AppManage.getSingleInstance().addActivity(this)


        //设置沉浸式状态栏
        StatusBarHelper.setStatusBar(this, false, true)
        StatusBarHelper.setStatusTextColor(true, this)
    }

    override fun onDestroy() {
        AppManage.getSingleInstance().removeActivity(this)
        super.onDestroy()
    }


    fun changeStateBarColor(color: Int) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color)
                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (e: Exception) {
            e.printStackTrace();
        }
    }


//   多状态界面开始

    protected var mHolder: Gloading.Holder? = null

    /**
     * make a Gloading.Holder wrap with current activity by default
     * override this method in subclass to do special initialization
     * @see SpecialActivity
     */
    open fun initLoadingStatusViewIfNeed() {
        if (mHolder == null) {
            mHolder = Gloading.getDefault().wrap(this).withRetry(Runnable { onLoadRetry() })
        }
    }


    public fun initMuiltStatusArea(view: View) {
        mHolder = Gloading.getDefault().wrap(view).withRetry(Runnable { onLoadRetry() })
    }

    open fun onLoadRetry() {
        // override this method in subclass to do retry task
    }

    fun showLoadingStatus() {
        initLoadingStatusViewIfNeed()
        mHolder!!.showLoading()
    }

    fun showLoadSuccessStatus() {
        initLoadingStatusViewIfNeed()
        mHolder!!.showLoadSuccess()
    }

    fun showLoadFailedStatus() {
        initLoadingStatusViewIfNeed()
        mHolder!!.showLoadFailed()
    }

    fun showEmptyStatus() {
        initLoadingStatusViewIfNeed()
        mHolder!!.showEmpty()
    }
//   多状态界面结束


}