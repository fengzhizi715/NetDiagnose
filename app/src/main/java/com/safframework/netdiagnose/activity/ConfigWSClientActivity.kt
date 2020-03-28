package com.safframework.netdiagnose.activity

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.widget.Toast
import com.safframework.netdiagnose.R
import com.safframework.netdiagnose.app.BaseActivity
import kotlinx.android.synthetic.main.activity_config_ws_client.*

/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.activity.ConfigWSClientActivity
 * @author: Tony Shen
 * @date: 2020-01-22 14:08
 * @version: V1.0 <描述当前版本功能>
 */
class ConfigWSClientActivity :BaseActivity(){

    override fun layoutId():Int = R.layout.activity_config_ws_client

    override fun initView() {

        intent.extras?.let {
            it.getString("url")?.let { url->
                url_edit.text = Editable.Factory.getInstance().newEditable(url)
                url_edit.setSelection(url.length)
            }
        }

        update.setOnClickListener {

            if (url_edit.text.isNotBlank() &&
                (url_edit.text.startsWith("ws://")|| url_edit.text.startsWith("wss://"))) {

                val intent = Intent(this@ConfigWSClientActivity, WebSocketClientActivity::class.java).apply {
                    putExtra("url", url_edit.text.toString())
                }

                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {

                Toast.makeText(this@ConfigWSClientActivity, "请输入正确的服务端地址", Toast.LENGTH_LONG).show()
            }
        }
    }
}