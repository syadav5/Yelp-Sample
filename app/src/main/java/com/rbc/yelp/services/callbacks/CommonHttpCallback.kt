package com.rbc.yelp.services.callbacks

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommonHttpCallback<T>(private val liveData: MutableLiveData<Resource<T>>) : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful && response.body() != null) {
            liveData.postValue(Resource.success(response.body()!!))
        } else {
            liveData.postValue(
                Resource.error(
                    message = response.message(),
                    null,
                    response.code().toString()
                )
            )
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        liveData.postValue(Resource.error(message = t.message, exception = t, errorCode = null))
    }

    fun showLoadingIndicator(){
        liveData.postValue(Resource.loading(null))
    }
}

data class Resource<T>(
    val status: Status,
    val data: T? = null,
    val exception: Throwable? = null,
    val message: String? = null,
    val errorCode: String? = null
) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(Status.SUCCESS, data)
        fun <T> loading(data: T?): Resource<T> = Resource(Status.LOADING, null)
        fun <T> error(message: String?, exception: Throwable?, errorCode: String?): Resource<T> =
            Resource(Status.ERROR, exception = exception, message = message, errorCode = errorCode)
    }

    enum class Status {
        SUCCESS, ERROR, LOADING
    }
}