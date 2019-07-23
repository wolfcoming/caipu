package com.example.caipuandroid.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.caipuandroid.ui.vo.User

/**
 * 收藏表
 */
@Entity(tableName = "collect_entity")
class CollectEntity {
    @PrimaryKey
    var id: Int = 0
    var name: String? = null
    var brief: String? = null
    var tips: String? = null
    var views: Int = 0
    var collect: Int = 0
    var makes: String? = null
    var burden: String? = null
    var img: String? = null
}