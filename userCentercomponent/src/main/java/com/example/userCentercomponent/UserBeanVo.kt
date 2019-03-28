package com.example.userCentercomponent

import java.io.Serializable

class UserBeanVo : Serializable {
    var name: String? = null
    var userId: String? = null
    var pwd: String? = null
    var headimg: String? = null
    var usertype: Int? = null
    var is_vip = false
}