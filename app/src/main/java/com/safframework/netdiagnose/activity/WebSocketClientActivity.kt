package com.safframework.netdiagnose.activity

import android.text.TextUtils
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.LinearLayoutManager
import com.safframework.ext.clickWithTrigger
import com.safframework.netdiagnose.R
import com.safframework.netdiagnose.adapter.MessageAdapter
import com.safframework.netdiagnose.app.BaseActivity
import com.safframework.netdiagnose.kotlin.delegate.viewModelDelegate
import com.safframework.netdiagnose.viewmodel.MainViewModel
import com.safframework.netdiagnose.viewmodel.WSClientViewModel
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

    private val wsClientViewModel by viewModelDelegate(WSClientViewModel::class)

    private val mSendMessageAdapter = MessageAdapter()
    private val mReceMessageAdapter = MessageAdapter()

    private var url:String = ""

    override fun layoutId(): Int = R.layout.activity_websocket_client

    override fun initView() {

        send_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        send_list.adapter = mSendMessageAdapter

        rece_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rece_list.adapter = mReceMessageAdapter

        config.clickWithTrigger {

        }

        connect.clickWithTrigger {
            if (url.isNotEmpty()) {
                wsClientViewModel.connect(url)
            }
        }

        send.clickWithTrigger{

            if (wsClientViewModel.getConnectStatus()) {

                val msg = send_et.text.toString()
                if (TextUtils.isEmpty(msg.trim { it <= ' ' })) {
                    return@clickWithTrigger
                }

                wsClientViewModel.send(msg)

                send_et.setText("")
            } else {

                Toast.makeText(this@WebSocketClientActivity, "未连接,请先连接", LENGTH_SHORT).show()
            }
        }

        clear.clickWithTrigger {
            mReceMessageAdapter.dataList.clear()
            mSendMessageAdapter.dataList.clear()
            mReceMessageAdapter.notifyDataSetChanged()
            mSendMessageAdapter.notifyDataSetChanged()
        }

        stop.clickWithTrigger {
            wsClientViewModel.stop()
        }
    }
}