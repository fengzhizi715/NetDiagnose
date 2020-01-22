package com.safframework.netdiagnose.connect.ws

import com.safframework.log.L
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.nio.ByteBuffer

/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.connect.ws.NettyWebSocketClient
 * @author: Tony Shen
 * @date: 2020-01-06 10:14
 * @version: V1.0 <描述当前版本功能>
 */
interface WSClientListener<T> {

    /**
     *
     * @param msg
     *
     */
    fun onMessageResponseClient(msg: T)
}

class NettyWebSocketClient(val serverURI:URI,val mListener: WSClientListener<String>): WebSocketClient(serverURI) {

    /**
     * 获取WS连接状态
     *
     */
    var connectStatus = false

    override fun onOpen(handshakedata: ServerHandshake) {
        L.i("new connection opened")
        connectStatus = true
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        L.i("closed with exit code $code additional info: $reason")
        connectStatus = false
    }

    override fun onMessage(message: String) {
        L.i("received message: $message")

        mListener.onMessageResponseClient(message)
    }

    override fun onMessage(message: ByteBuffer) {
        L.i("received ByteBuffer")
    }

    override fun onError(ex: Exception) {
        L.e("an error occurred:$ex")
        connectStatus = false
    }
}