package com.infoholdcity.baselibrary.view

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import com.infoholdcity.baselibrary.R
import java.lang.ref.WeakReference
import kotlin.math.sin

class SingleProgressDialog : Dialog {


    constructor(context: Context, theme: Int) : super(context, theme) {

    }

    companion object {
        private var mContext:WeakReference<Context>? = null;

        @Volatile
        var singleProgressDialog: SingleProgressDialog? = null

        fun showLoading(con: Context, message: String = "加载中", canCancel: Boolean = false) {

            mContext = WeakReference<Context>(con)
            if (singleProgressDialog != null && singleProgressDialog!!.isShowing) {
                singleProgressDialog!!.dismiss()
            }
            val context = mContext!!.get()
            if (context == null || !(context is Activity)) {
                return
            }

            singleProgressDialog = SingleProgressDialog(context, R.style.CustomProgressDialog)
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null)
            singleProgressDialog!!.setContentView(view)
            val tvMsg = view.findViewById<TextView>(R.id.tv_msg)
            tvMsg.text = message
            singleProgressDialog!!.setCancelable(canCancel)
            singleProgressDialog!!.setCanceledOnTouchOutside(false)
            if (singleProgressDialog != null && !singleProgressDialog!!.isShowing && !context.isFinishing) {
                singleProgressDialog!!.show()
            }

//            val lp: WindowManager.LayoutParams = singleProgressDialog!!.window.attributes
//            lp.gravity = Gravity.CENTER
//            singleProgressDialog!!.window.attributes = lp
        }

        fun hideLoading() {
            if (singleProgressDialog != null && singleProgressDialog!!.isShowing) {
                singleProgressDialog!!.dismiss()
            }
        }


    }
}