package com.example.crazygz.chat.factories.okhttp

import com.example.crazygz.chat.factories.okhttp.listener.DisposeDataListener
import com.example.crazygz.chat.factories.okhttp.request.CommonRequest
import com.example.crazygz.chat.factories.okhttp.request.RequestParams
import com.example.crazygz.chat.factories.okhttp.response.CommonJsonCallback
import okhttp3.*
import java.io.IOException
import java.util.*

public class CommonOkHttpClient {

    companion object {
        @JvmStatic
        fun instance(): CommonOkHttpClient = CommonOkHttpClient()
    }

    private val client = OkHttpClient()

    /**
     *  同步get请求
     */
    public fun executeGet(url: String,params: RequestParams): String? =
            client.newCall(CommonRequest.getRequest(url, params))
                    .execute()
                    .body()?.string()

    /**
     *  同步post请求
     */
    public fun executePost(url: String, objects: Objects?): String? =
            client.newCall(CommonRequest.postRequest(url, objects))
                    .execute()
                    .body()
                    ?.string()

    /**
     *  异步get请求
     */
    public fun <T : Any> get(url: String,
                             params: RequestParams,
                             listener: DisposeDataListener<T>) {
        client.newCall(CommonRequest.getRequest(url, params))
                .enqueue(CommonJsonCallback<T>(listener))
    }

    /**
     *  异步post请求
     */
    public fun <T : Any> post(url: String,
                              objects: Any?,
                              vararg listener: DisposeDataListener<T>) {
        client.newCall(CommonRequest.postRequest(url, objects))
                .enqueue(CommonJsonCallback<T>(listener[0]))
    }
}