package com.example.caipuandroid.remote.bean

import java.io.Serializable

/**
 * 接收解析接口数据
 */
class CategoryBean : Serializable {
    var id: Int = 0
    var name: String? = null
    var category_level: Int = 0
    var parent_category: Int = 0
}