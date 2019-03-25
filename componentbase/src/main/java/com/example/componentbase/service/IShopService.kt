package com.example.componentbase.service

import android.os.Bundle
import android.support.v4.app.Fragment

interface IShopService {
    fun getShopHomeFragment(bundle:Bundle?,tag:String = ""):Fragment
}