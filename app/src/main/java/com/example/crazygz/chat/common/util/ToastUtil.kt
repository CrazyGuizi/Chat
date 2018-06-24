package com.example.crazygz.chat.common.util

import android.widget.Toast
import com.example.crazygz.chat.common.app.BaseAppCompatActivity

public object ToastUtil {

    private var toast: Toast? = null

    fun show(message: String) {
        if (toast == null)
            toast = Toast.makeText(BaseAppCompatActivity.context, message, Toast.LENGTH_SHORT)
        else
            toast!!.setText(message)
        toast!!.show()
    }

    fun show(message: String, isLong: Boolean) {
        if (isLong) {
            if (toast == null)
                toast = Toast.makeText(BaseAppCompatActivity.context, message, Toast.LENGTH_LONG)
            else
                toast!!.setText(message)
            toast!!.show()
        }
        else
            show(message)
    }
}