package com.safframework.netdiagnose.delegate

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.safframework.netdiagnose.app.BaseActivity
import com.safframework.netdiagnose.viewmodel.BaseViewModel
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.delegate.ViewModelDelegate
 * @author: Tony Shen
 * @date: 2020-01-21 16:46
 * @version: V1.0 <描述当前版本功能>
 */
class ViewModelDelegate<out T : BaseViewModel>(private val clazz: KClass<T>, private val fromActivity: Boolean) {

    private var viewModel: T? = null

    operator fun getValue(thisRef: BaseActivity, property: KProperty<*>) = buildViewModel(activity = thisRef)

    operator fun getValue(thisRef: Fragment, property: KProperty<*>) = if (fromActivity)
        buildViewModel(activity = thisRef.activity as? BaseActivity
            ?: throw IllegalStateException("Activity must be as BaseActivity"))
    else buildViewModel(fragment = thisRef)

    private fun buildViewModel(activity: BaseActivity? = null, fragment: Fragment? = null): T {
        if (viewModel != null) return viewModel!!

        activity?.let {
            viewModel = ViewModelProviders.of(it).get(clazz.java)
        } ?: fragment?.let {
            viewModel = ViewModelProviders.of(it).get(clazz.java)
        } ?: throw IllegalStateException("Activity or Fragment is null! ")

        return viewModel!!
    }
}

fun <T : BaseViewModel> BaseActivity.viewModelDelegate(clazz: KClass<T>) = ViewModelDelegate(clazz, true)