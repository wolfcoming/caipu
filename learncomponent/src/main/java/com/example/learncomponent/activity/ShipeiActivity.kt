package com.example.learncomponent.activity

import android.os.Bundle
import com.example.learncomponent.R
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseActiviy
import kotlinx.android.synthetic.main.activity_shipei.*

class ShipeiActivity:BaseActiviy() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipei)
        save1.setOnClickListener {
            toast("save1")
        }

        save2.setOnClickListener {

        }

        btnget.setOnClickListener {
            toast("get")
        }
    }
}