package com.example.caipuandroid.service.impl

import android.os.SystemClock
import com.example.caipuandroid.remote.APIManage
import com.example.caipuandroid.remote.bean.BaseBean
import com.example.caipuandroid.remote.bean.CategoryBean
import com.example.caipuandroid.remote.util.ApiException
import com.example.caipuandroid.service.ICaipuService
import com.example.caipuandroid.ui.vo.CategoryVo
import com.google.gson.Gson
import io.reactivex.Observable
import java.lang.Exception
import kotlin.collections.ArrayList

/**
 * 该层用来转换业务层需要的数据格式
 */
class IServiceNetImpl : ICaipuService {

    override fun getCategorys(): Observable<List<CategoryVo>> {
        //将得道的网络数据转换成业务需要数据
        return APIManage.instance.apiService()
            .getCategorys()
            .map { listBaseBean ->

                val list = ArrayList<CategoryVo>()
                if (listBaseBean.code == 1) {
                    save2Db(listBaseBean)
                    //取出一级数据
                    listBaseBean.data!!.forEach {
                        if (it.category_level == 1) {
                            val categoryVo = CategoryVo()
                            categoryVo.categoryId = it.id
                            categoryVo.categoryLevel = it.category_level
                            categoryVo.categoryName = it.name
                            list.add(categoryVo)
                        }
                    }
                    for (categoryVo in list) {
                        dealData(categoryVo, listBaseBean.data!!)
                    }

                } else {
                    throw ApiException("${listBaseBean.message}")
                }
                list

            }
    }

    private fun save2Db(listBaseBean: BaseBean<List<CategoryBean>>) {
//        val categoryJson = Gson().toJson(listBaseBean)
//        val categoryEntity = CategoryEntity()
//        categoryEntity.categoryJson = categoryJson
//        categoryEntity.insetTime = SystemClock.currentThreadTimeMillis()
//        AppDatabase2.getInstance().CaipuDao().delete()
//        AppDatabase2.getInstance().CaipuDao().insertCategoryResult(categoryEntity)
    }


    fun dealData(categoryVo: CategoryVo, datas: List<CategoryBean>) {
        val list: ArrayList<CategoryVo> = ArrayList()
        for (data in datas) {
            if (data.category_level == 1) {
                continue
            }
            if (data != null && (data.parent_category == categoryVo.categoryId)) {
                val subCategoryVo = CategoryVo()
                subCategoryVo.categoryId = data.id
                subCategoryVo.categoryLevel = data.category_level
                subCategoryVo.categoryName = data.name
                list.add(subCategoryVo)
                //可优化的策略 ：将添加过的移除，避免多次判断
            }
        }
        categoryVo.subCategorys.addAll(list)
        for (categoryVo in categoryVo.subCategorys) {
            dealData(categoryVo, datas)
        }

    }


}