package com.safframework.netdiagnose.viewmodel

import androidx.lifecycle.MutableLiveData
import com.safframework.netdiagnose.app.BaseViewModel


/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.viewmodel.ServerAddressViewModel
 * @author: Tony Shen
 * @date: 2020-01-22 23:45
 * @version: V1.0 <描述当前版本功能>
 */
class ServerAddressViewModel:BaseViewModel() {

    private val address = MutableLiveData<String>()

    fun getAddress() = address
}