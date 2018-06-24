package com.example.crazygz.chat.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.example.crazygz.chat.MainActivity
import com.example.crazygz.chat.R
import com.example.crazygz.chat.common.app.BaseAppCompatActivity
import com.example.crazygz.chat.common.db.bean.User
import com.example.crazygz.chat.common.db.bean.UserManager
import com.example.crazygz.chat.common.util.LogUtil
import com.example.crazygz.chat.common.util.ToastUtil
import com.example.crazygz.chat.factories.okhttp.ClientManager
import com.example.crazygz.chat.factories.okhttp.CommonOkHttpClient
import com.example.crazygz.chat.factories.okhttp.OkHttpError
import com.example.crazygz.chat.factories.okhttp.ResultCallback
import com.example.crazygz.chat.factories.okhttp.exception.OkHttpException
import com.example.crazygz.chat.factories.okhttp.listener.DisposeDataListener
import com.example.crazygz.chat.factories.okhttp.request.RequestParams
import com.example.crazygz.chat.factories.okhttp.response.CommonJsonCallback
import com.example.crazygz.chat.factories.utils.HttpUrl
import okhttp3.*
import java.io.IOException

class LoginActivity : BaseAppCompatActivity(), View.OnClickListener {

    var loginState = true // 默认是登录状态
    var state: TextView? = null
    var fab: FloatingActionButton? = null
    var name: EditText? = null
    var username: EditText? = null
    var password: EditText? = null
    var submit: Button? = null

    private var userManager: UserManager = UserManager
    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    // 初始化控件
    private fun initView() {
        state = findViewById(R.id.tv_state)
        fab  = findViewById(R.id.fab)
        name = findViewById(R.id.et_name)
        username = findViewById(R.id.et_username)
        password = findViewById(R.id.et_password)
        submit = findViewById(R.id.btn_submit)

        submit!!.setOnClickListener(this)
        fab!!.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v!!.id) {
            R.id.fab -> {
                loginState = !loginState
                if (loginState) {
                    name!!.visibility = View.GONE
                    state!!.text = "Login"
                } else {
                    name!!.visibility = View.VISIBLE
                    state!!.text = "Register"
                }
            }
            R.id.btn_submit -> {if (loginState) login() else register()}
        }
    }

//    // 注册
//    private fun register() {
//        val n = name!!.text.toString()
//        val u = username!!.text.toString()
//        val p = password!!.text.toString()
//        LogUtil.d(TAG, "注册信息为：\n$n\n$u\n$p\n")
//
//        var clazz:Class<User> = User::class.java
//
//        if (n.isEmpty() || u.isEmpty() || p.isEmpty()) {
//            ToastUtil.show("信息不完整")
//        } else {
//            var client = CommonOkHttpClient.instance()
//            client.post<User>(HttpUrl.REGISTER,
//                    {
//                        var name = n
//                        var username = u
//                        var password = p
//                        var about = "一句话介绍你自己..."
//                    },
//                    object : DisposeDataListener<User> {
//                        override fun onSuccess(t: User) {
//                            userManager.user = t
//                            if (userManager.hasLogin()) {
//                                ToastUtil.show("注册成功，正在为您跳转")
//                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//                                finish()
//                            }
//                        }
//
//                        override fun onError(e: OkHttpException) {
//                            if (e.code == CommonJsonCallback.OPERATE_ERROR)
//                                ToastUtil.show(e.exception.toString())
//                        }
//                    })
//        }
//    }


    // 注册
    private fun register() {
        val n = name!!.text.toString()
        val u = username!!.text.toString()
        val p = password!!.text.toString()
        LogUtil.d(TAG, "注册信息为：\n$n\n$u\n$p\n")

        if (n.isEmpty() || u.isEmpty() || p.isEmpty()) {
            ToastUtil.show("信息不完整")
        } else {
            var map = HashMap<String, String>()
            map.put("name", n)
            map.put("username", u)
            map.put("password", p)
            map.put("about","一句话介绍你自己...")
            ClientManager.get(HttpUrl.REGISTER,
                    map,
                    object : ResultCallback {
                        override fun onSuccess(json: String) {
                            if (!json.isNullOrEmpty()) {
                                userManager.user = JSON.parseObject(json,
                                        User::class.java)
                                if (userManager.hasLogin()) {
                                    ToastUtil.show("注册成功，正在跳转")
                                    startActivity(Intent(this@LoginActivity,
                                            MainActivity::class.java))
                                    finish()
                                }
                            }
                        }

                        override fun onError(e: OkHttpError) {
                            ToastUtil.show(e.msg)
                        }
                    })
        }
    }


    private fun login() {
        val u = username!!.text.toString()
        val p = password!!.text.toString()


        LogUtil.d(TAG, "账号:$u\n密码：$p")

        if (u.isEmpty() || p.isEmpty()) {
            ToastUtil.show("请输入账号密码")
        } else {

            var map = HashMap<String, String>()
            map["username"] = u
            map["password"] = p

            ClientManager.get(HttpUrl.LOGIN, map,object : ResultCallback {
                override fun onSuccess(json: String) {
                    if (!json.isNullOrEmpty()) {
                        userManager.user = JSON.parseObject(json, User::class.java)
                        if (userManager != null) {
                            startActivity(Intent(this@LoginActivity,
                                    MainActivity::class.java))
                            ToastUtil.show(userManager.user!!.name + "已登录")
                            finish()
                        }
                    }
                }

                override fun onError(e: OkHttpError) {
                    ToastUtil.show(e.msg)
                }
            })




//            ClientManager.post(HttpUrl.LOGIN, object {
//                var username = "ldg"
//                var password = "123456"
//            },object : ResultCallback {
//                override fun onSuccess(json: String) {
//                    if (!json.isNullOrEmpty()) {
//                        userManager.user = JSON.parseObject(json, User::class.java)
//                        if (userManager != null) {
//                            startActivity(Intent(this@LoginActivity,
//                                    MainActivity::class.java))
//                            ToastUtil.show(userManager.user!!.name + "已登录")
//                            finish()
//                        }
//                    }
//                }
//
//                override fun onError(e: OkHttpError) {
//                    ToastUtil.show(e.msg)
//                }
//            })
        }
    }

//    // 登录
//    private fun login() {
//        val u = username!!.text.toString()
//        val p = password!!.text.toString()
//
//        var client = CommonOkHttpClient.instance()
////        var params: RequestParams = RequestParams()
////        params.put("username", u)
////        params.put("password", p)
//        LogUtil.d(TAG, "账号:$u\n密码：$p")
//
//        if (u.isEmpty() || p.isEmpty()) {
//            ToastUtil.show("请输入账号密码")
//        } else {
//            client.post<User>(HttpUrl.LOGIN, {
//                var username = u
//                var password = p
//            }, object : DisposeDataListener<User> {
//                override fun onSuccess(t: User) {
//                    userManager.user = t
//                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//                    finish()
//                    return
//                }
//
//                override fun onError(e: OkHttpException) {
//                    when(e.code) {
//                        CommonJsonCallback.OPERATE_ERROR ->
//                            ToastUtil.show(e.exception.toString())
//                    }
//                }
//            })
//        }
//
//    }

}
