package com.safframework.netdiagnose.activity

import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.safframework.ext.clickWithTrigger
import com.safframework.log.L
import com.safframework.netdiagnose.R
import com.safframework.netdiagnose.adapter.MessageAdapter
import com.safframework.netdiagnose.app.BaseActivity
import com.safframework.netdiagnose.domain.MessageBean
import com.safframework.netdiagnose.kotlin.delegate.viewModelDelegate
import com.safframework.netdiagnose.kotlin.extension.observe
import com.safframework.netdiagnose.kotlin.function.get
import com.safframework.netdiagnose.viewmodel.ServerAddressViewModel
import com.safframework.netdiagnose.viewmodel.TCPClientViewModel
import kotlinx.android.synthetic.main.activity_tcp_client.*
import kotlinx.android.synthetic.main.activity_tcp_client.clear
import kotlinx.android.synthetic.main.activity_tcp_client.config
import kotlinx.android.synthetic.main.activity_tcp_client.rece_list
import kotlinx.android.synthetic.main.activity_tcp_client.send
import kotlinx.android.synthetic.main.activity_tcp_client.send_et
import kotlinx.android.synthetic.main.activity_tcp_client.send_list
import kotlinx.android.synthetic.main.activity_tcp_client.server_address


/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.activity.TCPClientActivity
 * @author: Tony Shen
 * @date: 2020-01-22 14:59
 * @version: V1.0 <描述当前版本功能>
 */
class TCPClientActivity : BaseActivity() {

    private val tcpClientViewModel by viewModelDelegate(TCPClientViewModel::class)
    private val serverAddressViewModel by viewModelDelegate(ServerAddressViewModel::class)

    private val mSendMessageAdapter = MessageAdapter()
    private val mReceMessageAdapter = MessageAdapter()

    private var host:String = ""
    private var port:Int = 0

    private var flag:Boolean = false

    private val REQUEST_CODE_CONFIG:Int = 1000

    override fun layoutId(): Int = R.layout.activity_tcp_client

    override fun initView() {

        title = resources.getString(R.string.check_tcp)

        send_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        send_list.adapter = mSendMessageAdapter

        rece_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rece_list.adapter = mReceMessageAdapter

        config.clickWithTrigger {
            val intent = Intent(this@TCPClientActivity,ConfigTCPClientActivity::class.java).apply {
                putExtra("host",host)
                putExtra("port",port)
            }

            startActivityForResult(intent,REQUEST_CODE_CONFIG)
        }

        send.clickWithTrigger {

            val msg = send_et.text.toString()
            if (TextUtils.isEmpty(msg.trim { it <= ' ' })) {
                Toast.makeText(this@TCPClientActivity, "请输入发送的消息", Toast.LENGTH_SHORT).show()
                return@clickWithTrigger
            }

            if (host.isNotEmpty() && port>0) {

                tcpClientViewModel.getResult(msg,host,port,flag).observe(this, Observer {

                    L.i("result = ${it.get()}")

                    if (it.get() !is Exception) {
                        val messageBean = MessageBean(System.currentTimeMillis(), msg)
                        mSendMessageAdapter.dataList.add(0, messageBean)
                        runOnUiThread {
                            send_et.setText("")
                            mSendMessageAdapter.notifyDataSetChanged()
                        }

                        handlerMsg(it.get().toString())
                    }
                })

            } else {

                Toast.makeText(this@TCPClientActivity, "请先配置服务端地址，再发送消息", Toast.LENGTH_SHORT).show()
            }
        }

        rg.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_hex -> {
                    L.i("checked use hex")

                    flag  = true
                }
            }
        }

        clear.clickWithTrigger {
            mReceMessageAdapter.dataList.clear()
            mSendMessageAdapter.dataList.clear()
            mReceMessageAdapter.notifyDataSetChanged()
            mSendMessageAdapter.notifyDataSetChanged()
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

            host = data.getStringExtra("host")
            port = data.getStringExtra("port").toInt()

            L.i("host=$host, port=$port")

            serverAddressViewModel.getAddress().value = "服务端地址: host=$host, port=$port"
        }
    }
}