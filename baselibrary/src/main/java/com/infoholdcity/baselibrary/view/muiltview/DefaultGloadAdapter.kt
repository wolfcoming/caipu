package com.infoholdcity.baselibrary.view.muiltview

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.infoholdcity.baselibrary.view.muiltview.view.GlobalLoadingStatusView

class DefaultGloadAdapter : Gloading.Adapter {
    override fun getView(holder: Gloading.Holder, convertView: View?, status: Int): View {
        var loadingStatusView: GlobalLoadingStatusView? = null
        if (convertView != null && convertView is GlobalLoadingStatusView) {
            loadingStatusView = convertView as GlobalLoadingStatusView
        }
        if (loadingStatusView == null) {
            loadingStatusView = GlobalLoadingStatusView(holder.getContext(), holder.getRetryTask())
        }
        loadingStatusView!!.setStatus(status)
        return loadingStatusView
    }
}