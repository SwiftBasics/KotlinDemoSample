package com.basecode.viewmodel

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.basecode.network.Api
import javax.inject.Inject

public class SplashViewModel(context: Context) : BaseViewModel(context) {
    override fun renderView() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Log.e("TAG", "API called")
    }

    @Inject
    lateinit var postApi: Api

    private val _registerEvent = MutableLiveData<Int>()
    val registerEvent: LiveData<Int>
        get() = _registerEvent


    init {
        proceedFlow()
    }

    private fun proceedFlow() {
        Handler().postDelayed(Runnable {
            _registerEvent.value = 1
        }, 3000)
    }

    override fun onCleared() {
        super.onCleared()
    }
}

class CustomViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashViewModel(context) as T
    }
}

