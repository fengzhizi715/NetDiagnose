package com.safframework.netdiagnose.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.safframework.ext.clickWithTrigger
import com.safframework.netdiagnose.R
import com.safframework.netdiagnose.adapter.MessageAdapter
import com.safframework.netdiagnose.app.BaseActivity
import kotlinx.android.synthetic.main.activity_websocket_client.*

/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.activity.WebSocketClientActivity
 * @author: Tony Shen
 * @date: 2020-01-21 19:14
 * @version: V1.0 <描述当前版本功能>
 */
class WebSocketClientActivity : BaseActivity() {

    private val mSendMessageAdapter = MessageAdapter()
    private val mReceMessageAdapter = MessageAdapter()

    override fun layoutId(): Int = R.layout.activity_websocket_client

    override fun initView() {

        send_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        send_list.adapter = mSendMessageAdapter

        rece_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rece_list.adapter = mReceMessageAdapter

        config.clickWithTrigger {

        }

        connect.clickWithTrigger {

        }
    }
}