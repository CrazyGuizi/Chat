package com.example.crazygz.chat

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.crazygz.chat.fragment.ChatFragment
import com.example.crazygz.chat.fragment.FriendsFragment
import com.example.crazygz.chat.fragment.MeFragment
import kotlinx.android.synthetic.main.activity_main.*

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var fragments: List<Fragment>? = null
    private var currentIndexFragment: Int? = 0

    override fun onClick(v: View?) {

        var i = 0
        when(v) {
            tv_chat -> i = 0
            tv_friends -> i = 1
            tv_me -> i = 2
        }
        if (i != currentIndexFragment) {
            if(i == 2) {
                toolbar.hideOverflowMenu()
            } else {
                toolbar.showOverflowMenu()
            }
            showFragment(i)
            currentIndexFragment = i
        }
    }

    private fun showFragment(position: Int) {
        var transaction = supportFragmentManager.beginTransaction()
//        for (i in fragments!!.indices) {
//            if (i != position) {
//                transaction.hide(fragments!![i])
//            }
//        }
//        if (fragments!![position].isAdded) {
//            transaction.show(fragments!![position])
//        } else {
//            transaction.add(R.id.view_pager, fragments!![position])
//        }
        transaction.replace(R.id.view_pager, fragments!![position])
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initEvent()
    }

    private fun initEvent() {
        fragments = listOf(ChatFragment(), FriendsFragment(), MeFragment())
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.add(R.id.view_pager, fragments!![0])
        transaction.commit()
    }

    private fun initView() {

        toolbar.run {
            inflateMenu(R.menu.menu_nav)
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
//        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onPageSelected(position: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {
//
//            }
//        })
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
