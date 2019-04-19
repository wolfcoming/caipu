package com.example.componentbase.emptyImpl

import android.view.View
import com.example.componentbase.R
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseFragment
import com.infoholdcity.baselibrary.view.addressSelectView.*
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment:BaseFragment() {
    override fun inflateView(): Any {
        return R.layout.fragment_test
    }

    override fun initView(anchor: View) {

        val rootArea = Area()
        val list1 = ArrayList<Area>()
        for(i in 1..10){
            val firstArea = Area()
            firstArea.id = "first${i}"
            firstArea.name = "first${i}"
            firstArea.value = "first${i}"
            val list = ArrayList<Area>()
            for (j in 1..8){
                val secondArea = Area()
                secondArea.id = "first${i}_second${j}"
                secondArea.name = "first${i}_second${j}"
                secondArea.value = "first${i}_second${j}"
                list.add(secondArea)
            }
            firstArea.sublistArea =list
            list1.add(firstArea)
        }
        rootArea.sublistArea = list1

        btnAddress.setOnClickListener {

            val dialog =  BottomDialog(context)
            dialog.init(context,  SelectorNoDataProvider(context,rootArea,4))
            dialog.setOnAddressSelectedListener(object:SelectedListener{
                override fun onAddressSelected(selectAbles: java.util.ArrayList<ISelectAble>?) {
                    toast("hhhh")
                }
            })
            dialog.show()
        }

        btnAddress2.setOnClickListener {
            val dialog2 =  BottomDialog2(context)
            val slector = Selector(context,"",4)
            slector.setDataProvider(object : DataProvider {
                override fun provideData(currentId: Int, preId: String?, receiver: DataProvider.DataReceiver?) {
                    val list = ArrayList<ISelectAble>()
                    rootArea.sublistArea.forEach { area->
                        list.add(area)
                    }
                    receiver!!.send(list)
                }
            })
            dialog2.init(context, slector)
            dialog2.setOnAddressSelectedListener(object:SelectedListener{
                override fun onAddressSelected(selectAbles: java.util.ArrayList<ISelectAble>?) {
                    toast("hhhh")
                }
            })
            dialog2.show()
        }
    }
}