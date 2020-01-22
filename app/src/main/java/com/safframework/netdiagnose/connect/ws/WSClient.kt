package com.safframework.netdiagnose.connect.ws

import java.net.URI

/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.connect.ws.NettyClient
 * @author: Tony Shen
 * @date: 2020-01-06 12:39
 * @version: V1.0 <描述当前版本功能>
 */
object WSClient {

    private var client: NettyWebSocketClient? = null

    @JvmStatic
    fun connect(url:String,mListener: WSClientListener<String>) {

        stop()

        try {
            val serverURI = URI(url)
            client = NettyWebSocketClient(serverURI, mListener).apply {
                connect()
            }

        } catch (e: Exception) {

            e.printStackTrace()
        }
    }

    @JvmStatic
    fun getClient(): NettyWebSocketClient? = client

    /**
     * 客户端停止
     */
    @JvmStatic
    fun stop() {

        client?.let {

            if (!it.isClosed) {
                it.close()
            }
        }
    }
}
