package com.example.userCentercomponent

import android.os.Bundle
import android.view.View
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.baselibrary.base.BaseFragment
import com.infoholdcity.baselibrary.utils.SPUtils
import kotlinx.android.synthetic.main.usercenter_frgm_min.*

class MinFragment : BaseFragment() {

    companion object {

        fun Instance(bundle: Bundle): MinFragment {
            val fragment = MinFragment()
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun inflateView(): Any {
        return R.layout.usercenter_frgm_min
    }

    override fun initView(anchor: View) {
        val arg = arguments?.getString("args")
        val userBeanVo = SPUtils.getInstance(context).getObject<UserBeanVo>("userBean")
        if (userBeanVo == null) {

            tvInfo.text = "：：：：暂未登录"
        } else
            tvInfo.text = arg + "：：：：" + userBeanVo.name
    }
}