package com.safframework.netdiagnose.log

import com.safframework.log.printer.file.name.FileNameGenerator
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.log.NetdiagnoseFileNameGenerator
 * @author: Tony Shen
 * @date: 2020-01-18 21:26
 * @version: V1.0 <描述当前版本功能>
 */
class NetdiagnoseFileNameGenerator(val prefix:String) : FileNameGenerator {

    private val mLocalDateFormat: ThreadLocal<SimpleDateFormat> = object : ThreadLocal<SimpleDateFormat>() {

        override fun initialValue() = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
    }

    override fun isFileNameChangeable() = true

    override fun generateFileName(logLevel: Int, tag:String, timestamp: Long): String {

        var sdf = mLocalDateFormat.get()?: SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)

        return prefix + sdf.let {

            it.timeZone = TimeZone.getDefault()
            it.format(Date(timestamp))
        } + ".log"
    }
}