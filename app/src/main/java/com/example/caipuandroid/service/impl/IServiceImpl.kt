package com.example.caipuandroid.service.impl

import com.example.caipuandroid.remote.APIManage
import com.example.caipuandroid.remote.bean.BaseBean
import com.example.caipuandroid.remote.bean.CategoryBean
import com.example.caipuandroid.service.IService
import com.example.caipuandroid.ui.vo.CategoryVo
import io.reactivex.Observable
import java.util.*
import kotlin.collections.ArrayList

/**
 * 该层用来转换业务层需要的数据格式
 */
class IServiceImpl : IService {

    override fun getCategorys(): Observable<BaseBean<List<CategoryVo>>> {
        //将得道的网络数据转换成业务需要数据
        return APIManage.instance.apiService()
            .getCategorys()
            .map { listBaseBean ->
                val list = ArrayList<CategoryVo>()
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

                var result: BaseBean<List<CategoryVo>> = BaseBean();
                result.code = listBaseBean.code
                result.message = listBaseBean.message
                result.data = list
                result

            }
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