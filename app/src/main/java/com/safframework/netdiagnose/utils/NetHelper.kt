package com.safframework.netdiagnose.utils

import com.safframework.log.L
import java.io.IOException
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

    fun ping(timeout: Long = 30000): Boolean {
        try {
            val ip = "www.baidu.com"// ping 的地址，可以换成任何一种可靠的外网
            val status = executeCommand("ping -c 1 -w 100 $ip", timeout)
            // ping的状态
            return status == 0
        } catch (e: Exception) {
            L.d(TAG, "ping失败：" + e.message)
        } finally {
        }
        return false
    }

    /**
     * 运行一个外部命令，返回状态.若超过指定的超时时间，抛出TimeoutException
     *
     * @param command
     * @param timeout
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    @Throws(IOException::class, InterruptedException::class, TimeoutException::class)
    fun executeCommand(command: String, timeout: Long): Int? {
        val process = Runtime.getRuntime().exec(command)
        val worker = Worker(process)
        worker.start()
        try {
            worker.join(timeout)
            return if (worker.exit != null) {
                worker.exit
            } else {
                throw TimeoutException("连接超时")
            }
        } catch (ex: InterruptedException) {
            worker.interrupt()
            Thread.currentThread().interrupt()
            throw ex
        } finally {
            process.destroy()
        }
    }

    private class Worker constructor(private val process: Process) : Thread() {
        var exit: Int? = null

        override fun run() {
            try {
                exit = process.waitFor()
            } catch (ignore: InterruptedException) {
                L.d(TAG, ignore.message)
            }
        }
    }
}