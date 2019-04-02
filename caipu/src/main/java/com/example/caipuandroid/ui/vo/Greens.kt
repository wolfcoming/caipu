package com.example.caipuandroid.ui.vo

import java.io.Serializable

class Greens : Serializable {
    var id: Int = 0
    var name: String? = null
    var brief: String? = null
    var tips: String? = null
    var views: Int = 0
    var collect: Int = 0
    var makes: String? = null
    var burden: String? = null
    var img: String? = null
    var user: User? = null
    var userid: String? = null
}


data class User(val id: String, val name: String, val heading: String, val brief: String)