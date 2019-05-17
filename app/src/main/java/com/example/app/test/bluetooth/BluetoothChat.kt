package com.example.app.test.bluetooth

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import com.example.app.R
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseActiviy
import kotlinx.android.synthetic.main.activity_chat.*

/**
 * Author：yangqing
 * Time：2019/5/17 17:29
 * Description：
 */
class BluetoothChat : BaseActiviy() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val bluetoothDevice = intent.getBundleExtra("bundle").getParcelable<BluetoothDevice>("BluetoothDevice")
        btnSend.setOnClickListener {
            toast(bluetoothDevice.name)
        }

    }
}