package com.safframework.netdiagnose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.safframework.netdiagnose.utils.NetHelper

/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.viewmodel.MainViewModel
 * @author: Tony Shen
 * @date: 2020-01-21 17:01
 * @version: V1.0 <描述当前版本功能>
 */
class MainViewModel : BaseViewModel() {

    private var result = MutableLiveData<Boolean>()

    fun getPingResult(): LiveData<Boolean> {

        result.postValue(NetHelper.ping(3000))
        return result
    }
}