package com.safframework.netdiagnose.domain

import java.text.SimpleDateFormat

/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.domain.MessageBean
 * @author: Tony Shen
 * @date: 2020-01-22 00:05
 * @version: V1.0 <描述当前版本功能>
 */
class MessageBean(time: Long, var mMsg: String) {

    var mTime: String

    init {
        mTime = SimpleDateFormat("HH:mm:ss").format(time)
    }
}