package com.example.crazygz.chat.factories.okhttp.listener

import com.example.crazygz.chat.factories.okhttp.exception.OkHttpException

interface DisposeDataListener<T> {
    fun onSuccess(t: T)

    fun onError(e: OkHttpException)
}