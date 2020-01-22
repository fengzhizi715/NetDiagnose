package com.safframework.netdiagnose.viewmodel

import com.safframework.netdiagnose.app.BaseViewModel
import com.safframework.netdiagnose.connect.ws.WSClient
import com.safframework.netdiagnose.connect.ws.WSClientListener

/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.viewmodel.WSClientViewModel
 * @author: Tony Shen
 * @date: 2020-01-22 12:52
 * @version: V1.0 <描述当前版本功能>
 */
class WSClientViewModel : BaseViewModel() {

    fun connect(url:String,mListener: WSClientListener<String>) {

        WSClient.connect(url,mListener)
    }

    fun getConnectStatus():Boolean {

        return WSClient.getClient()?.let {

            it.connectStatus
        }?:false
    }

    fun send(msg:String) {

        WSClient.getClient()?.let {

            it.send(msg)
        }
    }

    fun stop() {

        WSClient.stop()
    }
}