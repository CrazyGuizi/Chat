package com.example.crazygz.chat.factories.okhttp

import android.os.Handler
import android.os.Looper
import android.service.carrier.CarrierMessagingService
import android.util.Log
import com.alibaba.fastjson.JSON
import com.example.crazygz.chat.common.util.LogUtil
import com.example.crazygz.chat.factories.okhttp.request.CommonRequest
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Type
import java.util.*


public interface ResultCallback {

    fun onSuccess(json: String);

    fun onError(e: OkHttpError)
}

public data class OkHttpError(var code: Int,var msg: String)

public object ClientManager {

    private const val TAG = "ClientManager";


    // 服务器定义API接口参数
    private const val NET_CODE = "code"
    private const val NET_MSG = "msg";
    private const val NET_DATA = "data";

    // 响应码
    private const val JSON_OK = 0 // json正确解析
    public const val EXCEPTION = 1
    public const val ERROR = 2

    private val TYPE_JSON: MediaType? = MediaType.parse("application/json; charset=utf-8")
    private val client = OkHttpClient()
    private val handler = Handler(Looper.getMainLooper())

    public fun get(url: String,map: Map<String, String>, callback:ResultCallback) {
        var request = Request.Builder()
                .url(getUrl(url, map))
                .build()
        handleResponse(request, callback)

    }

    // 处理响应
    private fun handleResponse(request: Request?, callback: ResultCallback) {
        client.newCall(request)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        handler.post {callback.onError(OkHttpError(EXCEPTION, "网络异常"))}
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        var body = response!!.body()!!.string()
                        var jsonObject = JSONObject(body)
                        if (jsonObject.has(NET_CODE)) { // 有响应码
                            handler.post {
                                if (jsonObject.getInt(NET_CODE) == JSON_OK) { // 处理成功
                                    callback.onSuccess(jsonObject.getString(NET_DATA))
                                } else { // 错误处理
                                    callback.onError(OkHttpError(ERROR, jsonObject.getString(NET_MSG)))
                                }
                            }
                        }
                    }
                })
    }

    public fun post(url: String, objects: Any, callback: ResultCallback) {
        Log.d(TAG, "解析为：\n" + JSON.toJSONString(objects))
        var body: RequestBody = RequestBody.create(TYPE_JSON,
                JSON.toJSONString(objects!!))
        var request = Request
                .Builder()
                .url(url)
                .post(body)
                .build()
        handleResponse(request, callback)
    }

    private fun getUrl(url: String, vararg map:Map<String, String>): String {
        var sb = StringBuilder(url)
        if (map.isNotEmpty()) {
            sb.append("?")
            for (m in map[0].entries) {
                sb.append("${m.key}=${m.value}&")
            }
        }
        LogUtil.d(TAG, sb.substring(0, sb.length - 1))
        return sb.substring(0, sb.length - 1)
    }
}