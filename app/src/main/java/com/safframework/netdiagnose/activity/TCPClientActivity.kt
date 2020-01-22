package com.safframework.netdiagnose.activity

import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.safframework.ext.clickWithTrigger
import com.safframework.log.L
import com.safframework.netdiagnose.R
import com.safframework.netdiagnose.adapter.MessageAdapter
import com.safframework.netdiagnose.app.BaseActivity
import com.safframework.netdiagnose.kotlin.delegate.viewModelDelegate
import com.safframework.netdiagnose.kotlin.function.get
import com.safframework.netdiagnose.viewmodel.TCPClientViewModel
import kotlinx.android.synthetic.main.activity_tcp_client.*


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

        }

        send.clickWithTrigger {

            val msg = send_et.text.toString()
            if (TextUtils.isEmpty(msg.trim { it <= ' ' })) {
                return@clickWithTrigger
            }

            if (host.isNotEmpty() && port>0) {

                tcpClientViewModel.getResult(msg,host,port,flag).observe(this, Observer {

                    L.i("result = ${it.get()}")
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
    }
}