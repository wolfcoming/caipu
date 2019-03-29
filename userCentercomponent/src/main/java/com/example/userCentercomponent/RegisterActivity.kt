package com.example.userCentercomponent

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.caipuandroid.base.BaseMvpActivity
import com.example.userCentercomponent.mp.contract.RegisterContract
import com.example.userCentercomponent.mp.contract.RegisterPresenter
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.config.ARouterConfig
import com.infoholdcity.baselibrary.view.SingleProgressDialog
import kotlinx.android.synthetic.main.activity_register.*

@Route(path = ARouterConfig.ACT_USER_GEGISTER)
class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), RegisterContract.RegisterView {
    override fun getPresenter(): RegisterPresenter {
        return RegisterPresenter()
    }

    override fun attachView(presenter: RegisterPresenter) {
        presenter.attachView(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        btnRegister.setOnClickListener {
            mPresenter!!.register()
        }
    }

    override fun showLoading() {
        super.showLoading()
        SingleProgressDialog.showLoading(this@RegisterActivity)
    }

    override fun hideLoading() {
        super.hideLoading()
        SingleProgressDialog.hideLoading()
    }

    override fun onError(message: String) {
        super.onError(message)
        toast(message)
    }


    override fun getUserName(): String {
        return etName.text.toString()
    }

    override fun getUserPwd(): String {
        return etPwd.text.toString()
    }

    override fun getPwdConfirm(): String {
        return etPwdConfirm.text.toString()
    }

    override fun registerSuccess() {
        toast("注册成功")
        hideLoading()
        finish()
    }
}