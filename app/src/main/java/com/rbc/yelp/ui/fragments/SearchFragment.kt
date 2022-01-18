package com.rbc.yelp.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.rbc.yelp.R
import com.rbc.yelp.databinding.FragmentHomeBinding
import com.rbc.yelp.services.callbacks.Resource
import com.rbc.yelp.utils.showToast
import com.rbc.yelp.viewmodels.HomeSharedViewModel
import com.rbc.yelp.viewmodels.HomeViewModel

/**
 * Search Fragment.
 */
class SearchFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding:FragmentHomeBinding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    private val sharedViewModel by activityViewModels<HomeSharedViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater)
        initUi()
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initUi() {
        uiDelegate.setToolbarTitle(getString(R.string.search_title), false)
        binding.apply {
            searchBtn.setOnClickListener {
                if (location.isNotEmpty()) {
                    sharedViewModel.selectedLocation = location.getText()
                    sharedViewModel.searchTerm = searchField.getText()
                    uiDelegate.hideKeyboard()
                    viewModel.search(
                        sharedViewModel.searchTerm,
                        sharedViewModel.selectedLocation!!
                    )
                } else {
                    showToast(requireContext(), "Please Enter a location")
                }
            }
        }
    }

    private fun initObservers() {
        viewModel.searchResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    uiDelegate.showProgress()
                }
                Resource.Status.SUCCESS -> {
                    uiDelegate.hideProgress()
                    sharedViewModel.searchResult = it.data
                    if(it.data?.businesses?.isEmpty() == true) {
                        showDialog(R.string.no_data_found)
                    } else {
                        navigationDelegate.navigateTo(ResultsFragment::class)
                    }
                }
                Resource.Status.ERROR -> {
                    uiDelegate.hideProgress()
                    showDialog(R.string.error_message)
                }
            }
        })
    }
    private fun showDialog(@StringRes msg: Int){
        AlertDialog.Builder(requireContext()).
        setTitle(R.string.error_title)
            .setMessage(msg)
            .setCancelable(true)
            .setPositiveButton(R.string.Ok) { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}