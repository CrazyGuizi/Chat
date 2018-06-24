package com.example.crazygz.chat.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.alibaba.fastjson.JSON
import com.example.crazygz.chat.R
import com.example.crazygz.chat.adapter.MessageAdapter
import com.example.crazygz.chat.common.app.BaseAppCompatActivity
import com.example.crazygz.chat.common.db.bean.Constanst
import com.example.crazygz.chat.common.db.bean.Message
import com.example.crazygz.chat.common.db.bean.UserManager
import com.example.crazygz.chat.common.util.ToastUtil
import com.example.crazygz.chat.factories.utils.HttpUrl
import kotlinx.android.synthetic.main.activity_chat.*
import java.io.OutputStream
import java.net.Socket

class ChatActivity : BaseAppCompatActivity(), View.OnClickListener {


    lateinit var title: String

    val user = UserManager.user

    lateinit var socket:Socket
    var adapter: MessageAdapter? = null
    private var messages: ArrayList<Message> = ArrayList<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        title = intent.getStringExtra("title")
        initEvent()
    }

    private fun initEvent() {

        toolbar.run{
            title = title
            titleColor = R.color.textWhite
        }

        var online =Message(Constanst.VIEW_TYPE_SERVER, user!!.name, "已上线")
        messages.add(online)
        online(online)
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = MessageAdapter(messages)
        recycler.adapter =adapter
        btn_send.setOnClickListener(this)

    }

    // 上线通知
    fun online(online: Message) {
        Thread {
            socket = Socket(HttpUrl.LOCAL_HOST, HttpUrl.SOCKET_PORT)
            var out = socket.getOutputStream()
            var json = JSON.toJSONString(online)
            out.write(json!!.toByteArray())
            out.flush()
        }.start()
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.btn_send -> sendMessage()
        }
    }

    // 发送消息
    private fun sendMessage() {
        var message = et_message.text.toString()
        if (message.isNullOrEmpty()) {
            ToastUtil.show("请先输入消息")
        } else {
            if (socket != null) {
                var m = Message(Constanst.VIEW_TYPE_SENDER, user!!.name, message)
                messages.add(m)
                adapter!!.notifyDataSetChanged()
                var out = socket.getOutputStream()
                var json = JSON.toJSONString(m)
                out.write(json.toByteArray())
                out.flush()
            }
        }

    }

}
