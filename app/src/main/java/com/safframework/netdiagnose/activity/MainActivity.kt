package com.safframework.netdiagnose.activity

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import com.safframework.ext.clickWithTrigger
import com.safframework.log.L
import com.safframework.netdiagnose.R
import com.safframework.netdiagnose.app.BaseActivity
import com.safframework.netdiagnose.kotlin.delegate.viewModelDelegate
import com.safframework.netdiagnose.kotlin.function.get
import com.safframework.netdiagnose.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.activity.MainActivity
 * @author: Tony Shen
 * @date: 2020-01-18 20:44
 * @version: V1.0 <描述当前版本功能>
 */
class MainActivity : BaseActivity() {

    private val mainViewModel by viewModelDelegate(MainViewModel::class)

    override fun layoutId(): Int = R.layout.activity_main

    override fun initView() {

        text1.clickWithTrigger {

            mainViewModel.getPingResult().observe(this, Observer {

                L.i("result = ${it.get()}")

                Toast.makeText(this@MainActivity,"result = ${it.get()}",Toast.LENGTH_LONG).show()
            })
        }

        text2.clickWithTrigger {

            startActivity(Intent(this@MainActivity,TCPClientActivity::class.java))
        }

        text3.clickWithTrigger {

            startActivity(Intent(this@MainActivity,WebSocketClientActivity::class.java))
        }
    }
}