package com.example.crazygz.chat.common.app

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

public open class BaseAppCompatActivity : AppCompatActivity() {

    companion object {
        public var context: Context? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = applicationContext
    }

}