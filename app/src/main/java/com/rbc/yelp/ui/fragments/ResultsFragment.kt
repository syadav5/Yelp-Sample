package com.rbc.yelp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rbc.yelp.databinding.ResultsFragmentBinding
import com.rbc.yelp.services.models.Business
import com.rbc.yelp.ui.adapters.SearchResultsAdapter
import com.rbc.yelp.viewmodels.HomeSharedViewModel

typealias businessCardItemClickListener = (Business) -> Unit

/**
 * Fragment shows Search Results
 */
class ResultsFragment : BaseFragment() {

    private var _binding: ResultsFragmentBinding? = null
    private val binding:ResultsFragmentBinding get() = _binding!!
    private val sharedViewModel: HomeSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ResultsFragmentBinding.inflate(inflater)
        initUi()
        return binding.root
    }

    private fun initUi(){
        binding.apply {
            uiDelegate.setToolbarTitle(
                getScreenTitle(
                    sharedViewModel.searchTerm,
                    sharedViewModel.selectedLocation
                )
            )
            initializeFilterOptions()
            resultsRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            resultsRecyclerView.setHasFixedSize(true)
            resultsRecyclerView.adapter = SearchResultsAdapter(sharedViewModel.dataList) { business ->
                sharedViewModel.selectedBusiness = business
                navigationDelegate.navigateTo(
                    BusinessDetailsFragment::class
                )
            }
            loadData()
        }
    }

    private fun initializeFilterOptions() {
        binding.categories.setOnCheckedChangeListener { button, checked ->
            if(checked) {
                button.text = "Sort Z-A"
                sharedViewModel.toggleSortOrder(false)
            } else {
                button.text = "Sort A-Z"
                sharedViewModel.toggleSortOrder(true)
            }
            loadData()
        }
        binding.openFilter.setOnCheckedChangeListener { compoundButton, b ->
            // todo Add Open filter to the list of filters and reload the data.
        }
    }

    private fun loadData(){
        sharedViewModel.loadData()
        binding.resultsRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun getScreenTitle(searchTerm: String?, selectedLocation: String?): String =
        "$searchTerm $selectedLocation"

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}