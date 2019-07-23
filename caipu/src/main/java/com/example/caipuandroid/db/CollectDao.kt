package com.example.caipuandroid.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Maybe

@Dao
interface CollectDao {
    /**
     * 插入收藏的菜信息
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCollectGreen(vararg collectEntity: CollectEntity)


    /**
     * 获取所有的收藏菜
     */
    @Query("select * from collect_entity")
    fun getAllCollectGreen(): Maybe<List<CollectEntity>>

    /**
     * 根据id获取收藏菜
     */
    @Query("select * from collect_entity where id=:id")
    fun getCollectByid(id: Int): Maybe<CollectEntity>

}