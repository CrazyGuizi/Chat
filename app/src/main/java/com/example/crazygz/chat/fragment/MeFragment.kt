package com.example.crazygz.chat.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.example.crazygz.chat.R
import com.example.crazygz.chat.common.db.bean.UserManager
import kotlinx.android.synthetic.main.fragment_me.*

class MeFragment : Fragment() {

    val user = UserManager.user

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_me, container, false)
        var name = view.findViewById<TextView>(R.id.tv_name)
        var about = view.findViewById<TextView>(R.id.tv_about)

        var changePassword = view.findViewById<LinearLayout>(R.id.ll_chang_password)
        var changeAbout = view.findViewById<LinearLayout>(R.id.ll_chang_about)
        var logout = view.findViewById<LinearLayout> (R.id.ll_quit)
        name.text = user!!.name
        about.text = user!!.about

        return view
    }


    companion object {
        @JvmStatic
        fun newInstance() = MeFragment()
    }
}
