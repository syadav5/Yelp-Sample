package com.rbc.yelp.services

import com.rbc.yelp.services.callbacks.CommonHttpCallback
import com.rbc.yelp.services.models.SearchResult
import retrofit2.Callback

class YelpApiClient {

    private val clientInstance = YelpRetrofit().retrofitInstance.create(YelpApi::class.java)

    /**
     * Search
     */
    fun search(searchTerm: String?, location: String, callback: CommonHttpCallback<SearchResult>) {
        callback.showLoadingIndicator()
        clientInstance.search(searchTerm ?: "", location).enqueue(callback)
    }
}
