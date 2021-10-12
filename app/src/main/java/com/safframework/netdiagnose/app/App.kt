package com.safframework.netdiagnose.app

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.safframework.netdiagnose.log.LogManager
import kotlin.properties.Delegates

/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.app.App
 * @author: Tony Shen
 * @date: 2020-01-18 21:40
 * @version: V1.0 <描述当前版本功能>
 */
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext

        initLog()
    }

    private fun initLog() {

        LogManager.initLog()
    }

    companion object {
        var CONTEXT: Context by Delegates.notNull()
    }
}