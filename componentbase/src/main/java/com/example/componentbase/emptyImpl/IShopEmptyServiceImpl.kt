package com.example.componentbase.emptyImpl

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.componentbase.service.IShopService

class IShopEmptyServiceImpl:IShopService {
    override fun getShopHomeFragment(bundle: Bundle?, tag: String): Fragment {
        return TestFragment()
    }
}