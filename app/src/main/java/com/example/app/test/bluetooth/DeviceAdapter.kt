package com.example.app.test.bluetooth

import android.bluetooth.BluetoothDevice
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.app.R

/**
 * Author：yangqing
 * Time：2019/5/17 16:46
 * Description：
 */
class DeviceAdapter : BaseQuickAdapter<BluetoothDevice, BaseViewHolder> {
    override fun convert(helper: BaseViewHolder?, item: BluetoothDevice?) {
        helper?.setText(R.id.tv_name, item!!.name)
    }

    constructor() : super(R.layout.item_device)
}