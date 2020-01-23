package com.safframework.netdiagnose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.safframework.netdiagnose.app.BaseViewModel
import com.safframework.netdiagnose.kotlin.function.Result
import com.safframework.netdiagnose.kotlin.function.resultFrom
import com.safframework.netdiagnose.utils.TCPUtils
import com.safframework.utils.RxJavaUtils
import io.reactivex.Observable

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

    fun getResult(cmd:String, host:String, port:Int, flag:Boolean): LiveData<Result<String, Exception>> {

        val result = resultFrom {

            Observable.create<String> {
                TCPUtils.sendMsgBySocket(cmd,host, port,flag)
            }
            .compose(RxJavaUtils.observableToMain())
            .blockingFirst()
        }

        liveData.postValue(result)
        return liveData
    }
}