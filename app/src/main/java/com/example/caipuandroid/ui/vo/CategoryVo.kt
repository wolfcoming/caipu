package com.example.caipuandroid.ui.vo

/**
 * 在ui界面中使用的数据（业务bean）
 */
class CategoryVo {
    var categoryId: Int = 0
    var categoryName: String? = null
    var categoryLevel: Int = 1
    val subCategorys: ArrayList<CategoryVo> = ArrayList()
    var isSelect: Boolean = false
}