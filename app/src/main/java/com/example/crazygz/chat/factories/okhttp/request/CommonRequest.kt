package com.example.crazygz.chat.factories.okhttp.request

import android.util.Log
import com.alibaba.fastjson.JSON
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import java.lang.StringBuilder
import java.util.*

public class CommonRequest {

    companion object {
        private const val TAG = "CommonRequest";
        private val TYPE_JSON: MediaType? = MediaType.parse("application/json; charset=utf-8")

        public fun getRequest(url: String, params: RequestParams): Request {
            var sb = StringBuilder(url)
            sb.append("?")
            if (params != null) {
                for (p in params.urlParams!!.entries) {
                    sb.append("${p.key}=${p.value}&")
                }
            }
            Log.d(TAG, sb.substring(0, sb.length - 1))
            return Request
                    .Builder()
                    .url(sb.substring(0, sb.length - 1))
                    .get()
                    .build()
        }

        public fun postRequest(url: String, objects: Any?): Request? {
            var body: RequestBody = RequestBody.create(TYPE_JSON,JSON.toJSONString(objects!!))
            return Request
                    .Builder()
                    .url(url)
                    .post(body)
                    .build()
        }
    }



}