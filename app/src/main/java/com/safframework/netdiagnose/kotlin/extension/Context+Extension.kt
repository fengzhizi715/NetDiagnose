package com.safframework.netdiagnose.kotlin.extension

import android.content.Context
import android.content.pm.PackageManager

/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.kotlin.extension.`Context+Extension`
 * @author: Tony Shen
 * @date: 2020-01-21 15:56
 * @version: V1.0 <描述当前版本功能>
 */

/**
 * 获取当前app的版本号
 */
fun Context.getAppVersion(): String {

    val appContext = applicationContext
    val manager = appContext.getPackageManager()
    try {
        val info = manager.getPackageInfo(appContext.getPackageName(), 0)

        if (info != null)
            return info.versionName

    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    return ""
}