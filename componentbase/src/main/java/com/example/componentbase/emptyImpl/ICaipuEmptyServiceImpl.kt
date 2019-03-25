package com.example.componentbase.emptyImpl

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.example.componentbase.service.ICaipuService
import com.example.componentbase.service.IUserCenterService

class ICaipuEmptyServiceImpl : ICaipuService {
    override fun getCategoryFragment(bundle: Bundle?, tag: String): Fragment {
        return TestFragment()
    }

}