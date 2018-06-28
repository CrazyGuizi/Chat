package com.example.crazygz.chat.factories.socket

import android.accounts.NetworkErrorException
import android.os.Handler
import android.os.Looper
import com.alibaba.fastjson.JSON
import com.example.crazygz.chat.common.db.bean.Constant
import com.example.crazygz.chat.common.db.bean.Message
import com.example.crazygz.chat.common.db.bean.UserManager
import com.example.crazygz.chat.common.util.LogUtil
import com.example.crazygz.chat.common.util.ToastUtil
import com.example.crazygz.chat.factories.utils.HttpUrl
import java.io.*
import java.net.Socket
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


// 消息处理回调
public interface MessageListener {
    fun getMessage(m: Message); // 获得从服务器返回的消息
}

public class ClientSocket{

    private val TAG = "ClientSocket"
    private var executor: ExecutorService = Executors.newCachedThreadPool()
    private var handler: Handler = Handler(Looper.getMainLooper()) // 切换到主线程，处理消息
    private var user = UserManager.user
    private var isClose = false

    private lateinit var clientSocket: Socket
    private lateinit var reader: BufferedReader
    private lateinit var writer: BufferedWriter
    public lateinit var listener: MessageListener

    public fun connect(message: Message) {
        executor.execute {
            clientSocket = Socket(HttpUrl.LOCAL_HOST, HttpUrl.SOCKET_PORT)
            var inputStream = clientSocket.getInputStream()
            var outputStream = clientSocket.getOutputStream()
            reader = BufferedReader(InputStreamReader(inputStream))
            writer = BufferedWriter(OutputStreamWriter(outputStream))

            // 上线通知
            sendMessage(message)
            // 接收服务器信息
            receiveMessage()
        }
    }

    // 发送消息
    public fun sendMessage(m: Message) {
        if (clientSocket != null && clientSocket.isConnected) {
            executor.execute{
                var json = toJson(m)
                writer.write(json)
                writer.flush()
            }
        } else {
            throw NetworkErrorException("socket连接出错")
        }
    }

    private fun toJson(m: Message) = JSON.toJSONString(m) + "\n"

    // 接收信息
    private fun receiveMessage() {
        if (clientSocket != null && clientSocket.isConnected) {
            if (!clientSocket.isClosed) {
                executor.execute {
                    while (!isClose) { // 循环读取来自服务器发送的消息
                        var json: String? = null
                        do {
                        json = reader.readLine()
                            if (!json.isNullOrEmpty() ) { // 收到消息了
                                LogUtil.d(TAG, "收到的消息为：" + json)
                                var m = JSON.parseObject(json, Message::class.java)
                                if (listener != null) {
                                    handleMessage(m) // 切换到主线程处理消息
                                } else {
                                    throw Exception("回调接口对象未实例化，请调用setListener()方法设置")
                                }
                            }
                        } while (!json!!.isNullOrEmpty())
                    }
                }
            } else {
                isClose = true
                throw IOException("socket已经关闭")
            }
        } else {
            throw NetworkErrorException("socket连接出错")
        }
    }

    private fun handleMessage(m: Message) {
        handler.post {
            listener.getMessage(m)
        }
    }

    // 关闭socket
    public fun close() {
        try {
            if (clientSocket != null) {
                clientSocket.close()
            }
            if (reader != null) {
                reader.close()
            }
            if (writer != null) {
                writer.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isClose = true
            executor.shutdownNow()
        }
    }

}
