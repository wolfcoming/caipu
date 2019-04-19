package com.example.componentbase.emptyImpl

import android.view.View
import com.example.componentbase.R
import com.infoholdcity.basearchitecture.self_extends.Klog
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseFragment
import com.infoholdcity.baselibrary.view.addressSelectView.*
import com.infoholdcity.baselibrary.view.addressSelectView.i.DataProvider
import com.infoholdcity.baselibrary.view.addressSelectView.i.SelectedListener
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment : BaseFragment() {
    override fun inflateView(): Any {
        return R.layout.fragment_test
    }

    override fun initView(anchor: View) {

        val rootArea = TreeData()
        val list1 = ArrayList<TreeData>()
        for (i in 1..2) {
            val firstArea = TreeData()
            firstArea.id = "first${i}"
            firstArea.name = "first${i}"
            firstArea.value = "first${i}"
            val list = ArrayList<TreeData>()
            for (j in 1..2) {
                val secondArea = TreeData()
                secondArea.id = "first${i}_second${j}"
                secondArea.name = "first${i}_second${j}"
                secondArea.value = "first${i}_second${j}"
                list.add(secondArea)
                val list2 = ArrayList<TreeData>()
                if (j == 2 ) {
                    for (k in 1..2) {
                        val thridData = TreeData();
                        thridData.id = "first${i}_second${j}_thrid${k}"
                        thridData.name = "first${i}_second${j}_thrid${k}"
                        thridData.value = "first${i}_second${j}_thrid${k}"
                        list2.add(thridData)
                    }
                    secondArea.sublistTreeData = list2
                }

            }
            firstArea.sublistTreeData = list
            list1.add(firstArea)
        }
        rootArea.sublistTreeData = list1

        btnAddress.setOnClickListener {

            val dialog = BottomDialog(context)
            dialog.init(
                context,
                SelectorNoDataProvider(context, rootArea, 5)
            )
            dialog.setOnAddressSelectedListener {
                toast("hhhh")
                for (treeData in it) {
                    Klog.e(contents = treeData.value)
                }
                dialog.dismiss()
            }
            dialog.show()
        }

        btnAddress2.setOnClickListener {
            val dialog2 = BottomDialog(context)
            val slector = Selector(context, "", 4)
            slector.setDataProvider { currentId, preId, receiver ->
                val data: TreeData? = TreeData.getAreaById(rootArea, preId)
                receiver!!.send(data?.sublistTreeData)
            }
            dialog2.init(context, slector)
            dialog2.setOnAddressSelectedListener {
                for (treeData in it) {
                    Klog.e(contents = treeData.value)
                }
                toast("hhhh")
                dialog2.dismiss()
            }
            dialog2.show()
        }
    }
}