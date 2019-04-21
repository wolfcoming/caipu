package com.example.caipuandroid.ui.vo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull

/**
 * 在ui界面中使用的数据（业务bean）
 */
open class CategoryVo {
    var categoryId: Int = 0
    var categoryName: String? = null
    var imgurl: String? = null
    var categoryLevel: Int = 1
    val subCategorys: ArrayList<CategoryVo> = ArrayList()
    var isSelect: Boolean = false
}