package com.example.shopingmodule.adapter.home

import com.example.shopingmodule.ui.vo.ShopBannerVo
import com.example.shopingmodule.ui.vo.ShopCategoryVo
import java.io.Serializable

data class ContentBean(
    val type: Int,
    val categorys: List<ShopCategoryVo>? = null,
    val goods: List<ShopBannerVo>? = null
) : Serializable {
}