package com.example.crazygz.chat.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.crazygz.chat.R
import com.example.crazygz.chat.common.db.bean.Constant
import com.example.crazygz.chat.common.db.bean.Message
import com.example.crazygz.chat.common.util.LogUtil

public class MessageAdapter(private var messages: ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TAG = "MessageAdapter"
    }

    public fun addItem(m: Message) {
        messages.add(m)
        notifyDataSetChanged()

    }

    public fun addItemFirst(m: Message) {
        messages.add(0, m)
        notifyItemInserted(0)
    }

    public fun remove(m: Message) {
        var position = messages.indexOf(m)
        if (position != -1) {
            messages.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    public fun removeAll() {
        notifyItemRangeRemoved(0, messages.size)
        messages.clear()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ServerMessageHolder) {
            holder.onBind(messages[position])
            LogUtil.d(TAG, "布局为系统提醒")
        } else if (holder is MessageHolder) { // 收发消息
            if (messages[position].type == Constant.VIEW_TYPE_RECEIVER) {
                holder.onBind(true, messages[position])
                LogUtil.d(TAG, "布局为接收消息")
            } else if (messages[position].type == Constant.VIEW_TYPE_SENDER){
                holder.onBind(false, messages[position])
                LogUtil.d(TAG, "布局为发送消息")
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return messages[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == Constant.VIEW_TYPE_SERVER) { // 系统提醒
            val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.server_item, parent,false)

            return ServerMessageHolder(view)
        } else {
            val  view =
                    LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
            return MessageHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    //  系统提醒
    private inner class ServerMessageHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        private var server:TextView = itemView!!.findViewById(R.id.tv_server_message)
        fun onBind(message:Message) {
            server.text = "系统提醒：${message.name}${message.message}"
        }
    }

    // 接收和发送
    private inner class MessageHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

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

        fun onBind(isReceiver: Boolean, message: Message) {
            if (isReceiver) {
                layoutS.visibility = View.GONE
                receiver.text = message.name
                receiverMessage.text = message.message
            } else {
                layoutR.visibility = View.GONE
                sender.text = message.name
                senderMessage.text = message.message
            }
        }
    }
}
