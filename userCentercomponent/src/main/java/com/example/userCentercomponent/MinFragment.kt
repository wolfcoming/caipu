package com.example.userCentercomponent

import android.os.Bundle
import android.view.View
import com.example.componentbase.eventbus.UserEvent
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseFragment
import com.infoholdcity.baselibrary.utils.SPUtils
import kotlinx.android.synthetic.main.usercenter_frgm_min.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        Klog.e(contents = "onDestroy")
        EventBus.getDefault().unregister(this)
    }

    override fun onStop() {
        super.onStop()
        Klog.e(contents = "onStop")
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updateUserInfo(userinfo: UserEvent) {
        val userBeanVo = SPUtils.getInstance(context).getObject<UserBeanVo>("userBean")
        tvInfo.text = userBeanVo.name!!
    }


}