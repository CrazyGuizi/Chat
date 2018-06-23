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
import com.example.crazygz.chat.MainActivity
import com.example.crazygz.chat.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    var loginState = true // 默认是登录状态
    var state: TextView? = null
    var fab: FloatingActionButton? = null
    var name: EditText? = null
    var username: EditText? = null
    var password: EditText? = null
    var submit: Button? = null

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

    // 注册
    private fun register() {
        val n = name!!.text.toString()
        val u = username!!.text.toString()
        val p = password!!.text.toString()
        if (n.isEmpty() || u.isEmpty() || p.isEmpty()) {
            Toast.makeText(this, "信息不完整", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show()
        }
    }

    // 登录
    private fun login() {
        val u = username!!.text.toString()
        val p = password!!.text.toString()
        if (u == "ldg" && p == "123456") {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show()
        }
    }


}
