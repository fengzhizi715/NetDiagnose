package com.safframework.netdiagnose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.safframework.kotlin.coroutines.IO
import com.safframework.kotlin.coroutines.exception.UncaughtCoroutineExceptionHandler
import com.safframework.log.L
import com.safframework.netdiagnose.app.BaseViewModel
import com.safframework.netdiagnose.kotlin.function.Result
import com.safframework.netdiagnose.kotlin.function.resultFrom
import com.safframework.netdiagnose.utils.TCPUtils
import kotlinx.coroutines.launch

/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.viewmodel.TCPClientViewModel
 * @author: Tony Shen
 * @date: 2020-01-22 15:26
 * @version: V1.0 <描述当前版本功能>
 */
class TCPClientViewModel : BaseViewModel() {

    private var liveData = MutableLiveData<Result<String,Exception>>()

    private val handler = UncaughtCoroutineExceptionHandler {

        L.e(it.localizedMessage)
    }

    fun getResult(cmd:String, host:String, port:Int, flag:Boolean): LiveData<Result<String,Exception>> {

        viewModelScope.launch(IO + handler) {

            val value = resultFrom {
                TCPUtils.sendMsgBySocket(cmd, host, port, flag)
            }

            liveData.postValue(value)
        }

        return liveData
    }
}