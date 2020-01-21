package com.safframework.netdiagnose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.safframework.netdiagnose.utils.NetHelper
import com.safframework.netdiagnose.kotlin.function.Result
import com.safframework.netdiagnose.kotlin.function.resultFrom

/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.viewmodel.MainViewModel
 * @author: Tony Shen
 * @date: 2020-01-21 17:01
 * @version: V1.0 <描述当前版本功能>
 */
class MainViewModel : BaseViewModel() {

    private var liveData = MutableLiveData<Result<Boolean,Exception>>()

    fun getPingResult(): LiveData<Result<Boolean,Exception>> {

        val result = resultFrom {
            NetHelper.ping()
        }

        liveData.postValue(result)
        return liveData
    }
}