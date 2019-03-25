package com.example.caipuandroid.outService

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.caipuandroid.ui.fragment.Frgm_Category
import com.example.componentbase.service.ICaipuService

class CaipuOutService : ICaipuService {
    override fun getCategoryFragment(bundle: Bundle?, tag: String): Fragment {
        val fragment = Frgm_Category()
        fragment.arguments = bundle
        return fragment
    }
}