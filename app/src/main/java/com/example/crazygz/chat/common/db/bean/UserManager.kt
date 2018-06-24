package com.example.crazygz.chat.common.db.bean

public object UserManager {

    var user: User? = null

    fun hasLogin() = user != null

    fun logout() = {user = null}
}