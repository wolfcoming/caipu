package com.example.userCentercomponent

import android.graphics.*
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.example.componentbase.ServiceFactory
import com.example.componentbase.eventbus.UserEvent
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseFragment
import com.infoholdcity.baselibrary.config.ARouterConfig
import com.infoholdcity.baselibrary.utils.SPUtils
import kotlinx.android.synthetic.main.usercenter_frgm_min.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth


class MinFragment : BaseFragment() {

    companion object {

        fun Instance(bundle: Bundle?): MinFragment {
            val fragment = MinFragment()
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun inflateView(): Any {
        return R.layout.usercenter_frgm_min
    }

    override fun initView(anchor: View) {


        changeUserState()

        btnLogin.setOnClickListener {
            //跳转到登录界面
            ARouter.getInstance().build(ARouterConfig.ACT_USER_LOGIN).navigation()
        }

        btnLoginOut.setOnClickListener {
            UserOperation.userLoginOut(context!!)
        }


    }

    fun getOvalBitmap(bitmap: Bitmap): Bitmap {

        val output = Bitmap.createBitmap(
            bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)

        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.getWidth(), bitmap.getHeight())

        val rectF = RectF(rect)

        paint.setAntiAlias(true)
        canvas.drawARGB(0, 0, 0, 0)
        paint.setColor(color)

        canvas.drawCircle(
            rectF.top / 2 + rectF.bottom / 2, rectF.top / 2 + rectF.bottom / 2,
            rectF.top / 2 + rectF.bottom / 2, paint
        )

        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

    private fun changeUserState() {
        if (ServiceFactory.instance.getUsercenterService().isLogin()) {
            btnLogin.visibility = View.GONE
            btnLoginOut.visibility = View.VISIBLE
            val name = ServiceFactory.instance.getUsercenterService().getUserName()
            tvName.text = name
        } else {
            tvName.text = ""
            btnLogin.visibility = View.VISIBLE
            btnLoginOut.visibility = View.GONE
        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updateUserInfo(userinfo: UserEvent) {
        changeUserState()
    }


}

