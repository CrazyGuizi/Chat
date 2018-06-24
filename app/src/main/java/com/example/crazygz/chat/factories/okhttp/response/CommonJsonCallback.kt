package com.example.crazygz.chat.factories.okhttp.response

import android.os.Handler
import android.os.Looper
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.example.crazygz.chat.common.util.LogUtil
import com.example.crazygz.chat.factories.okhttp.exception.OkHttpException
import com.example.crazygz.chat.factories.okhttp.listener.DisposeDataListener
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Type
import kotlin.reflect.KClass

public class CommonJsonCallback<T : Any> (private var listener: DisposeDataListener<T>?) : Callback {

    private var delivery: Handler = Handler(Looper.getMainLooper())

    companion object {
        private const val TAG = "CommonJsonCallback"

        // 服务器定义API接口参数
        private const val NET_CODE = "code"
        private const val NET_MSG = "msg";
        private const val NET_DATA = "data";

        // 响应码
        private const val JSON_OK = 0 // json正确解析
        public const val OPERATE_ERROR = 1; // 不是预期的结果（json什么的都正确，但不是正确结果）
        private const val NETWORK_ERROR = -1; // 网络错误
        private const val JSON_ERROR = -2; // json解析异常
        private const val OTHER_ERROR = -3; // 其他错误
    }

    override fun onFailure(call: Call?, e: IOException?) {
        delivery!!.post(Runnable { listener!!.onError(OkHttpException(NETWORK_ERROR, e!!)) })
    }

    override fun onResponse(call: Call?, response: Response?) {
        delivery!!.post(Runnable { handleResponse(response) })
    }

    // 处理响应
    private fun handleResponse(response: Response?) {

        if (response == null) { // 响应体为空
            listener!!.onError(OkHttpException(NETWORK_ERROR, response!!))
            LogUtil.d(TAG, "处理出错：响应体为空")
        } else{ // 响应体不为空
            var body = response!!.body()!!.string()
            var jsonObject = JSONObject(body)
            if (jsonObject.has(NET_CODE)) { // 含有响应码
                if (jsonObject.getInt(NET_CODE) == JSON_OK) { // 响应码正确
                    LogUtil.d(TAG, "正在处理：\n" + jsonObject.getString(NET_DATA))
                    val data = jsonObject.getString(NET_DATA)
//                    var c = JSON.parseObject(data, clazz)
//                    if (clazz != null) {
//                        listener!!.onSuccess(c)
//                        LogUtil.d(TAG, "处理成功：" + c.toString())
//                    } else {
//                        listener!!.onError(OkHttpException(JSON_ERROR, "解析错误"))
//                        LogUtil.d(TAG, "处理出错，json转化为实体类失败")
//                    }
                } else { // 响应码不正确
                    LogUtil.d(TAG, "处理正常，但结果不对：\n$body")
                    listener!!.onError(OkHttpException(OPERATE_ERROR, jsonObject.get(NET_MSG)) )
                }
            } else { // 不含响应码
                LogUtil.d(TAG, "处理出错，响应码为空")
                listener!!.onError(OkHttpException(OTHER_ERROR, body))
            }
        }
    }

}