package com.safframework.netdiagnose.utils

import java.io.Closeable
import java.io.IOException

/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.utils.IOUtils
 * @author: Tony Shen
 * @date: 2019-10-10 17:13
 * @version: V1.0 <描述当前版本功能>
 */
/**
 * 安全关闭io流
 * @param closeable
 */
fun closeQuietly(closeable: Closeable?) {

    closeable?.let {
        try {
            it.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}