package com.rbc.yelp.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveData<T>: MutableLiveData<T>() {
    private val hasHandledChanges = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer {
            if(hasHandledChanges.compareAndSet(true,false)){
                observer.onChanged(it)
            }
        })
    }

    override fun setValue(value: T) {
        hasHandledChanges.set(true)
        super.setValue(value)
    }

    fun call(){
        postValue(null)
    }
}