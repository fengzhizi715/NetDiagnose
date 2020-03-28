package com.safframework.netdiagnose.kotlin.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.kotlin.extension.`LifecycleOwner+Extension`
 * @author: Tony Shen
 * @date: 2020-01-21 14:21
 * @version: V1.0 <描述当前版本功能>
 */
fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {

    liveData.observe(this, Observer {
        it?.let { t -> action(t) }
    })
}