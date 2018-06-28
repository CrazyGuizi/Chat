package com.example.crazygz.chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import com.example.crazygz.chat.common.app.BaseAppCompatActivity
import com.example.crazygz.chat.common.db.bean.User
import com.example.crazygz.chat.common.db.bean.UserManager
import com.example.crazygz.chat.common.util.LogUtil
import com.example.crazygz.chat.fragment.ChatFragment
import com.example.crazygz.chat.fragment.FriendsFragment
import com.example.crazygz.chat.fragment.MeFragment
import kotlinx.android.synthetic.main.activity_main.*

const val TAG = "MainActivity"

class MainActivity : BaseAppCompatActivity(), View.OnClickListener {

    private var fragments: List<Fragment>? = null
    private val titles = arrayListOf<String>("消息","好友","个人")
    private var currentIndexFragment: Int? = 0 // 当前fragment索引
    private val user = UserManager.user

    override fun onClick(v: View?) {

        var i = 0
        when(v) {
            tv_chat -> i = 0
            tv_friends -> i = 1
            tv_me -> i = 2
        }
        if (i != currentIndexFragment) {
            showFragment(i)
            currentIndexFragment = i
        }
    }

    private fun showFragment(position: Int) {
        var transaction = supportFragmentManager.beginTransaction()
        for (i in fragments!!.indices) {
            if (i != position) {
                transaction.hide(fragments!![i])
            }
        }
        transaction.show(fragments!![position])
        toolbar.title = titles[position]
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initFragments()
    }

//    // 保存user数据
//    override fun onSaveInstanceState(outState: Bundle?) {
//        if (outState != null) {
//            outState.putInt("id", user!!.id)
//            outState.putString("name", user!!.name)
//            outState.putString("username", user!!.username)
//            outState.putString("password", user!!.password)
//            outState.putString("about", user!!.about)
//        }
//        super.onSaveInstanceState(outState)
//    }

//    // 恢复数据
//    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
//        if (savedInstanceState != null) {
//            UserManager.user = User(savedInstanceState.getInt("id"),
//                    savedInstanceState.getString("name"),
//                    savedInstanceState.getString("username"),
//                    savedInstanceState.getString("password"),
//                    savedInstanceState.getString("about"))
//            LogUtil.d(TAG, "恢复数据" +  user.toString())
//        }
//
//    }


    // 创建fragment并加载
    private fun initFragments() {
        fragments = listOf(ChatFragment(), FriendsFragment(), MeFragment())
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        for (f in fragments!!) {
            transaction.add(R.id.container, f)
            transaction.hide(f)
        }
        transaction.show(fragments!![0])
        transaction.commit()
    }

    @SuppressLint("ResourceAsColor")
    private fun initView() {

        // Toolbar相关设置
        toolbar.run {
            inflateMenu(R.menu.menu_nav)
            setTitleTextColor(R.color.textWhite)
            setOnMenuItemClickListener {
                when(it .itemId) {
                R.id.menu_add_friends -> addFriends()
                R.id.menu_create_group -> createGroup()
            }
                true }
        }

        tv_chat.setOnClickListener(this)
        tv_friends.setOnClickListener(this)
        tv_me.setOnClickListener(this)
    }


    // 创建群组
    private fun createGroup() {
        Log.d(TAG, "createGroup")
    }

    // 添加好友
    private fun addFriends() {
        Log.d(TAG,"addFriends")
    }


}
