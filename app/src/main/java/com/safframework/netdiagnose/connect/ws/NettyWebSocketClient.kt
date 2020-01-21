package com.safframework.netdiagnose.connect.ws

import com.safframework.log.L
import com.safframework.log.LogLevel
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
interface NettyClientListener<T> {

    /**
     *
     * @param msg
     *
     */
    fun onMessageResponseClient(msg: T)
}

class NettyWebSocketClient(val serverURI:URI,val mListener: NettyClientListener<String>): WebSocketClient(serverURI) {

    override fun onOpen(handshakedata: ServerHandshake) {
        L.i("new connection opened")
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        L.i("closed with exit code $code additional info: $reason")
    }

    override fun onMessage(message: String) {
        L.i("received message: $message")

        mListener.onMessageResponseClient(message)
    }

    override fun onMessage(message: ByteBuffer) {
        L.i(msg = "received ByteBuffer")
    }

    override fun onError(ex: Exception) {
        L.i(logLevel = LogLevel.ERROR, msg = "an error occurred:$ex")
    }

    fun sendCommand(text: String) {
        send(text)
    }
}