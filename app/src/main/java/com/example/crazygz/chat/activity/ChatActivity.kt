package com.example.crazygz.chat.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.crazygz.chat.R
import com.example.crazygz.chat.adapter.MessageAdapter
import com.example.crazygz.chat.common.app.BaseAppCompatActivity
import com.example.crazygz.chat.common.db.bean.Constant
import com.example.crazygz.chat.common.db.bean.Message
import com.example.crazygz.chat.common.db.bean.UserManager
import com.example.crazygz.chat.common.util.ToastUtil
import com.example.crazygz.chat.factories.socket.ClientSocket
import com.example.crazygz.chat.factories.socket.MessageListener
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : BaseAppCompatActivity(), View.OnClickListener {


    lateinit var title: String

    private val user = UserManager.user

    private lateinit var client: ClientSocket
    private var adapter: MessageAdapter? = null
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

        // 一进入聊天室就发送系统提醒
        var online =Message(Constant.VIEW_TYPE_SERVER,
                user!!.name,
                user!!.username,
                "已上线")
        messages.add(online)
        client = ClientSocket()
        client.connect(online) // 连接socket并且发送上线消息
        // 注册回调
        client.listener = object : MessageListener {
            override fun getMessage(m: Message) {
                adapter!!.addItem(m)
            }
        }

        recycler.layoutManager = LinearLayoutManager(this)
        adapter = MessageAdapter(messages)
        recycler.adapter =adapter

        btn_send.setOnClickListener(this)

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
            // 将内容包装为Message对象
            et_message.text.clear()
            var m = Message(Constant.VIEW_TYPE_SENDER,
                    user!!.name,
                    user!!.username,
                    message)
            adapter!!.addItem(m)
            client.sendMessage(m) // 发送给服务器，让服务器转发给其他人
        }
    }


    private fun notifyMessage(m: Message?) {
        runOnUiThread {
            if (m != null) {
                messages.add(m)
                adapter!!.notifyDataSetChanged()
            }
        }
    }

    // 关闭资源
    override fun finish() {
        //  client.close()
        super.finish()
    }

}
