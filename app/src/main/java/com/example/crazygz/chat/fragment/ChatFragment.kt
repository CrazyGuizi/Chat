package com.example.crazygz.chat.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.example.crazygz.chat.R
import com.example.crazygz.chat.activity.ChatActivity
import com.example.crazygz.chat.common.db.bean.UserManager

class ChatFragment : Fragment() {

     public val TAG: String = "ChatFragment"

    var user = UserManager.user

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_chat, container, false)
        val chat = view.findViewById<LinearLayout>(R.id.ll_chat)
        val roomName = view.findViewById<TextView>(R.id.tv_room_name)
        chat.setOnClickListener {
            var i = Intent(context, ChatActivity::class.java)
            i.putExtra("title",roomName!!.text.toString())
            startActivity(i)
        }
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = ChatFragment()
    }
}
