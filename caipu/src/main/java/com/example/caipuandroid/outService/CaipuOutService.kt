package com.example.caipuandroid.outService

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.caipuandroid.ui.fragment.Caipu_Frgm_Home
import com.example.caipuandroid.ui.fragment.CollectFragment
import com.example.caipuandroid.ui.fragment.Frgm_Category
import com.example.componentbase.service.ICaipuService

class CaipuOutService : ICaipuService {
    override fun getCollectFragment(bundle: Bundle?, tag: String): Fragment {
        val fragment = CollectFragment()
        fragment.arguments = bundle
        return fragment
    }

    override fun getCaipuHomeFragment(bundle: Bundle?, tag: String): Fragment {
        val fragment = Caipu_Frgm_Home()
        fragment.arguments = bundle
        return fragment
    }

    override fun getCategoryFragment(bundle: Bundle?, tag: String): Fragment {
        val fragment = Frgm_Category()
        fragment.arguments = bundle
        return fragment
    }
}