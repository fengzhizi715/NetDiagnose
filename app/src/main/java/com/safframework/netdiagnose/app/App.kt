package com.safframework.netdiagnose.app

import android.app.Application
import com.safframework.netdiagnose.log.LogManager

/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.app.App
 * @author: Tony Shen
 * @date: 2020-01-18 21:40
 * @version: V1.0 <描述当前版本功能>
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initLog()
    }

    private fun initLog() {

        LogManager.initLog()
    }
}