package com.rbc.yelp.ui.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import ui.NavigationDelegate
import ui.UiDelegate

open class BaseFragment: Fragment() {

    lateinit var uiDelegate: UiDelegate
    lateinit var navigationDelegate: NavigationDelegate

    override fun onAttach(context: Context) {
        super.onAttach(context)
        uiDelegate = requireActivity() as UiDelegate
        navigationDelegate = requireActivity() as NavigationDelegate
    }
}