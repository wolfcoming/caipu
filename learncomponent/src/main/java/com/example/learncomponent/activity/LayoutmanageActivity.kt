package com.example.learncomponent.activity

import android.os.Bundle
import com.example.learncomponent.R
import com.example.learncomponent.adapter.SimpleAdapter
import com.example.learncomponent.adapter.SimpleAdapter3
import com.example.learncomponent.layoutmanage.MyLayouManage
import com.infoholdcity.baselibrary.base.BaseActiviy
import kotlinx.android.synthetic.main.activity_layoutmanage.*

class LayoutmanageActivity : BaseActiviy() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layoutmanage)

        recycle.layoutManager = MyLayouManage()
        val mAdapter = SimpleAdapter3()
        recycle.adapter = mAdapter
        for(i in 0..200){
            mAdapter.addData(i.toString())
        }


    }
}