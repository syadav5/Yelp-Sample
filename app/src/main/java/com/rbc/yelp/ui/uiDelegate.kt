package ui

import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

interface UiDelegate {
    fun showProgress()
    fun hideProgress()
    fun setToolbarTitle(title:String, showBackButton: Boolean = true)
    fun hideToolbar()
    fun hideKeyboard()
}

interface NavigationDelegate {
    fun <T:Fragment> navigateTo(fragmentKClass: KClass<T>)
}