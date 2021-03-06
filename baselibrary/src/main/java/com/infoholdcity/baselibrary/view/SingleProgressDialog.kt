package com.infoholdcity.baselibrary.view

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.infoholdcity.baselibrary.R
import java.lang.ref.WeakReference

class SingleProgressDialog : Dialog {

    constructor(context: Context, theme: Int) : super(context, theme)

    companion object {
        private var weakReference: WeakReference<Context>? = null;

        @Volatile
        var singleProgressDialog: SingleProgressDialog? = null

        fun showLoading(con: Context, message: String = "加载中", canCancel: Boolean = false) {
            var context: Context? = null
            if (weakReference != null) {
                context = weakReference!!.get()
            } else {
                weakReference = WeakReference(con)
                context = weakReference!!.get()
            }

            if (singleProgressDialog != null && singleProgressDialog!!.isShowing) {
                return
            }
            if (context == null || !(context is Activity)) {
                return
            }

            var tvMsg: TextView? = null
            if (singleProgressDialog == null) {
                singleProgressDialog = SingleProgressDialog(context, R.style.CustomProgressDialog)
                val view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null)
                singleProgressDialog!!.setContentView(view)
                tvMsg = view.findViewById<TextView>(R.id.tv_msg)
                tvMsg.text = message
            }

            tvMsg?.text = message
            singleProgressDialog!!.setCancelable(canCancel)
            singleProgressDialog!!.setCanceledOnTouchOutside(false)
            if (singleProgressDialog != null && !singleProgressDialog!!.isShowing && !context.isFinishing) {
                if (context != null)
                    singleProgressDialog!!.show()
            }
        }

        fun hideLoading() {
            if (singleProgressDialog != null && singleProgressDialog!!.isShowing) {
                singleProgressDialog!!.dismiss()
            }
            if (weakReference != null) {
                weakReference = null
            }
        }
    }
}