package com.safframework.netdiagnose.activity

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.widget.Toast
import com.safframework.netdiagnose.R
import com.safframework.netdiagnose.app.BaseActivity
import kotlinx.android.synthetic.main.activity_config_tcp_client.*


/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.activity.ConfigTCPClientActivity
 * @author: Tony Shen
 * @date: 2020-01-23 14:16
 * @version: V1.0 <描述当前版本功能>
 */
class ConfigTCPClientActivity : BaseActivity(){

    override fun layoutId():Int = R.layout.activity_config_tcp_client

    override fun initView() {

        intent.extras?.let {

            it.getString("host")?.let {host ->
                host_edit.text = Editable.Factory.getInstance().newEditable(host)
                host_edit.setSelection(host.length)
            }

            it.getInt("port",0)?.let {port->
                port_edit.text = Editable.Factory.getInstance().newEditable(port.toString())
                port_edit.setSelection(port_edit.text.toString().length)
            }

        }

        update.setOnClickListener {

            if (host_edit.text.isNotBlank()) {

                val intent = Intent(this@ConfigTCPClientActivity, WebSocketClientActivity::class.java).apply {
                    putExtra("host", host_edit.text.toString())
                    putExtra("port", port_edit.text.toString())
                }

                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {

                Toast.makeText(this@ConfigTCPClientActivity, "请输入服务端地址", Toast.LENGTH_LONG).show()
            }
        }
    }
}