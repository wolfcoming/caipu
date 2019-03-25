package com.example.shopingmodule.outservice

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.componentbase.service.IShopService
import com.example.shopingmodule.ui.Frgm_ShopMain

class ShopOutServiceImpl : IShopService {
    override fun getShopHomeFragment(bundle: Bundle?, tag: String): Fragment {
        val fragment = Frgm_ShopMain()
        fragment.arguments = bundle
        return fragment

    }
}