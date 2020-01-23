package com.safframework.netdiagnose.activity

import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.LinearLayoutManager
import com.safframework.ext.clickWithTrigger
import com.safframework.log.L
import com.safframework.netdiagnose.R
import com.safframework.netdiagnose.adapter.MessageAdapter
import com.safframework.netdiagnose.app.BaseActivity
import com.safframework.netdiagnose.connect.ws.WSClientListener
import com.safframework.netdiagnose.domain.MessageBean
import com.safframework.netdiagnose.kotlin.delegate.viewModelDelegate
import com.safframework.netdiagnose.kotlin.extension.observe
import com.safframework.netdiagnose.viewmodel.ServerAddressViewModel
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
    private val serverAddressViewModel by viewModelDelegate(ServerAddressViewModel::class)

    private val mSendMessageAdapter = MessageAdapter()
    private val mReceMessageAdapter = MessageAdapter()

    private var url:String = ""

    private val REQUEST_CODE_CONFIG:Int = 1000

    private val mListener = object : WSClientListener<String> {

        override fun onMessageResponseClient(msg: String) {

            handlerMsg(msg)
        }
    }

    override fun layoutId(): Int = R.layout.activity_websocket_client

    override fun initView() {

        title = resources.getString(R.string.check_ws)

        send_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        send_list.adapter = mSendMessageAdapter

        rece_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rece_list.adapter = mReceMessageAdapter

        config.clickWithTrigger {

            val intent = Intent(this@WebSocketClientActivity,ConfigWSClientActivity::class.java).apply {
                putExtra("url",url)
            }

            startActivityForResult(intent,REQUEST_CODE_CONFIG)

        }

        connect.clickWithTrigger {

            if (wsClientViewModel.getConnectStatus()) {

                Toast.makeText(this@WebSocketClientActivity, "已经连接，无需重复连接", LENGTH_SHORT).show()
            } else {
                if (url.isNotEmpty()) {
                    wsClientViewModel.connect(url,mListener)
                } else {
                    Toast.makeText(this@WebSocketClientActivity, "请先配置服务端地址，再进行连接", LENGTH_SHORT).show()
                }
            }
        }

        send.clickWithTrigger{

            if (wsClientViewModel.getConnectStatus()) {

                val msg = send_et.text.toString()
                if (TextUtils.isEmpty(msg.trim { it <= ' ' })) {
                    Toast.makeText(this@WebSocketClientActivity, "请输入发送的消息", LENGTH_SHORT).show()
                    return@clickWithTrigger
                }

                wsClientViewModel.send(msg)
                val messageBean = MessageBean(System.currentTimeMillis(), msg)
                mSendMessageAdapter.dataList.add(0, messageBean)
                runOnUiThread {
                    send_et.setText("")
                    mSendMessageAdapter.notifyDataSetChanged()
                }
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

        this.observe(serverAddressViewModel.getAddress()) {
            server_address.text = it
        }

        serverAddressViewModel.getAddress().value = "服务端地址:"
    }

    /**
     * 处理服务端收到的信息
     */
    private fun handlerMsg(message: String) {
        val messageBean = MessageBean(System.currentTimeMillis(), message)
        mReceMessageAdapter.dataList.add(0, messageBean)
        runOnUiThread { mReceMessageAdapter.notifyDataSetChanged() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_CONFIG && data!=null) {

            url = data.getStringExtra("url")

            L.i("url=$url")

            serverAddressViewModel.getAddress().value = "服务端地址:$url"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        wsClientViewModel.stop()
    }
}