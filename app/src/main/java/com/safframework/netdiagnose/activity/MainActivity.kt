package com.safframework.netdiagnose.activity

import androidx.lifecycle.Observer
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

        text1.setOnClickListener {

            mainViewModel.getPing().observe(this, Observer {

                L.i("result = "+it.get())
            })
        }

    }
}