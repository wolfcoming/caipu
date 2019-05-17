package com.example.app.test.bluetooth

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import com.example.app.R
import com.infoholdcity.basearchitecture.self_extends.toast
import com.infoholdcity.baselibrary.base.BaseActiviy
import kotlinx.android.synthetic.main.actvity_bluetooth.*
import android.bluetooth.BluetoothDevice.ACTION_FOUND
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.infoholdcity.basearchitecture.self_extends.Klog


/**
 * Author：yangqing
 * Time：2019/5/17 15:59
 * Description：
 */
class BluetoothActivity : BaseActiviy() {

    companion object {
        val REQUEST_ENABLE_BT = 0
        val defaultAdapter = BluetoothAdapter.getDefaultAdapter()
    }

    var deviceAdapter: DeviceAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_bluetooth)
        //注册蓝牙搜索广播信息
        registerBluetooth()
        mRv.layoutManager = LinearLayoutManager(this)
        deviceAdapter = DeviceAdapter()
        mRv.adapter = deviceAdapter
        mRv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        deviceAdapter?.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(this, BluetoothChat::class.java)
            val bundle = Bundle()
            bundle.putParcelable("BluetoothDevice", adapter.data[position] as BluetoothDevice)
            intent.putExtra("bundle", bundle)
            startActivity(intent)
        }


        btnSearch.setOnClickListener {
            deviceAdapter?.data?.clear()
            //检测蓝牙是否打开
            if (!defaultAdapter.isEnabled) {
                toast("请打开蓝牙")
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            } else {
                toast("蓝牙已经打开")
                search()
            }
        }
    }

    private fun registerBluetooth() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND)
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        registerReceiver(receiver, intentFilter)
    }

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.action.equals(BluetoothDevice.ACTION_FOUND)) {
                val device: BluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                if (device != null) {
                    if (device.name != null && device.address != null) {
                        deviceAdapter?.addData(device)
                        deviceAdapter?.notifyDataSetChanged()
                    }
                }
                Klog.e(contents = "搜索结果：" + device.name)
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.action)) {
                Klog.e(contents = "search finish!!")
            }
        }
    }


    private fun search() {
        if (defaultAdapter.isDiscovering) {
            defaultAdapter.cancelDiscovery()
        }
        defaultAdapter.startDiscovery()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        } else {
            search()
        }
    }
}