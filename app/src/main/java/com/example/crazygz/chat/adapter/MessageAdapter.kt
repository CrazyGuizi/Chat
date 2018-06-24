package com.example.crazygz.chat.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.crazygz.chat.R
import com.example.crazygz.chat.common.db.bean.Constanst
import com.example.crazygz.chat.common.db.bean.Message

public class MessageAdapter(var message: ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (message[position].type == Constanst.VIEW_TYPE_SERVER) { // 系统提示
            if (holder is ServerMessageHolder) {
                holder.onBind(message[position])
            }
        } else {
            if (holder is MessageHolder) { // 收发消息
                if (message[position].type == Constanst.VIEW_TYPE_RECEIVER) {
                    holder.onBind(true, position)
                } else if (message[position].type == Constanst.VIEW_TYPE_SENDER){
                    message
                    holder.onBind(false, position)
                }
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return message[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, null)
        return MessageHolder(view)
    }

    override fun getItemCount(): Int {
        return message.size
    }

    //  系统提醒
    inner class ServerMessageHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        private var server:TextView = itemView!!.findViewById(R.id.tv_server_message)
        fun onBind(message:Message) {
            server.text = "系统提醒：${message.name}${message.message}"
        }
    }

    // 接收和发送
    inner class MessageHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        private var isReceiver: Boolean = false
        private lateinit var receiver:TextView
        private lateinit var receiverMessage:TextView
        private lateinit var sender:TextView
        private lateinit var senderMessage:TextView
        private lateinit var layoutR:LinearLayout
        private lateinit var layoutS:LinearLayout

        init {
            this.receiver = itemView!!.findViewById<TextView>(R.id.tv_receiver)
            this.receiverMessage = itemView!!.findViewById<TextView>(R.id.tv_receiver_massage)
            this.sender = itemView!!.findViewById<TextView>(R.id.tv_sender)
            this.senderMessage = itemView!!.findViewById<TextView>(R.id.tv_send_message)
            layoutR = itemView.findViewById(R.id.ll_receiver)
            layoutS = itemView.findViewById(R.id.ll_sender)
        }

        fun onBind(isReceiver: Boolean, position: Int) {
            if (isReceiver) {
                layoutS.visibility = View.GONE
                receiver.text = message[position].name
                receiverMessage.text = message[position].message
            } else {
                layoutR.visibility = View.GONE
                sender.text = message[position].name
                senderMessage.text = message[position].message
            }
        }
    }


}
