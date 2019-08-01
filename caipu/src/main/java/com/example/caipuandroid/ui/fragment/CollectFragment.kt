package com.example.caipuandroid.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.caipuandroid.R
import com.example.caipuandroid.db.AppDatabase
import com.example.caipuandroid.ui.adapter.CollectAdapter
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragmen_collect.*

class CollectFragment : BaseFragment() {
    override fun inflateView(): Any {
        return R.layout.fragmen_collect
    }

    var subscribe: Disposable? = null
    var adapter: CollectAdapter? = null
    override fun initView(anchor: View) {

        mRv.layoutManager = LinearLayoutManager(context)
        adapter = CollectAdapter()
        mRv.adapter = adapter
        getData()
    }

    private fun getData() {
        subscribe = AppDatabase.getCollectDao().getAllCollectGreen()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                adapter?.setNewData(it)
            }, {
                toast(it.message!!)
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        subscribe.let {
            subscribe?.dispose()
        }
    }
}