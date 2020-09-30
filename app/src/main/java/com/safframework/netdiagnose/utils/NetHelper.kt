package com.safframework.netdiagnose.utils

import cn.netdiscovery.command.Appender
import cn.netdiscovery.command.CommandBuilder
import cn.netdiscovery.command.CommandExecutor
import com.safframework.log.L
import java.io.IOException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.utils.NetHelper
 * @author: Tony Shen
 * @date: 2019-09-10 22:25
 * @version: V1.0 <描述当前版本功能>
 */
object NetHelper {

    val TAG = "NetHelper"

    fun ping(): Boolean {
        val ip = "www.baidu.com"// ping 的地址，可以换成任何一种可靠的外网

        val cmd = CommandBuilder("ping").withArgs("-c","1","$ip").build()

        val status = CommandExecutor.executeSync(cmd, null,5, TimeUnit.SECONDS,object : Appender {
            override fun appendStdText(text: String) {
            }

            override fun appendErrText(text: String) {
            }

        }).getExecutionResult().exitValue()

        L.d("status = $status")

        // ping 的状态
        return status == 0
    }
}