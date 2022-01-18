package com.rbc.yelp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbc.yelp.services.YelpApiClient
import com.rbc.yelp.services.callbacks.CommonHttpCallback
import com.rbc.yelp.services.callbacks.Resource
import com.rbc.yelp.services.models.SearchResult
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val _searchResponse: SingleLiveData<Resource<SearchResult>> = SingleLiveData()
    val searchResponse:LiveData<Resource<SearchResult>> = _searchResponse

    private val apiClient by lazy {
        YelpApiClient()
    }

    fun search(searchTerm: String?, location : String) {
        viewModelScope.launch {
            apiClient.search(searchTerm, location, CommonHttpCallback(_searchResponse))
        }
    }

    fun clear(){
        _searchResponse.call()
    }
}