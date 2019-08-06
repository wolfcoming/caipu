package com.infoholdcity.baselibrary.config


class ARouterConfig {
    companion object {
        //菜谱组件
        const val ACT_CAIPU_CATEGORY = "/caipu/ACT_CAIPU_CATEGORY"//分类界面
        const val ACT_CAIPU_HOME = "/caipu/ACT_CAIPU_HOME"//菜谱首页
        const val ACT_CAIPU_ADD = "/caipu/ACT_CAIPU_ADD"//菜谱增加界面
        const val ACT_CAIPU_LIST = "/caipu/ACT_CAIPU_LIST"//菜谱列表
        const val ACT_CAIPU_DETAIL = "/caipu/ACT_CAIPU_DETAIL"//菜谱详情界面


        //购物模块
        const val ACT_SHOP_HOME = "/shop/ACT_SHOP_HOME"


        //用户中心模块
        const val ACT_USER_LOGIN = "/user/ACT_USER_LOGIN"
        const val ACT_USER_GEGISTER = "/user/ACT_USER_GEGISTER"


        //通用actvity  嵌套fragment
        const val  ACT_COMMON = "/base/ACT_COMMON"


        //94mm
        const val ACT_MM94HOME = "/mm94/ACT_MM94HOME"

    }

}