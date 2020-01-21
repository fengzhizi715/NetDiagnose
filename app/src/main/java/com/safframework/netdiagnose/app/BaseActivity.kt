package com.safframework.netdiagnose.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.app.BaseActivity
 * @author: Tony Shen
 * @date: 2020-01-18 20:44
 * @version: V1.0 <描述当前版本功能>
 */
abstract class BaseActivity : AppCompatActivity() {

    abstract fun layoutId(): Int

    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        initView()
    }
}