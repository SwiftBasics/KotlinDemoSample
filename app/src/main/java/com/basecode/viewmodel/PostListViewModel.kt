package com.basecode.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.basecode.model.LoginData
import com.basecode.network.APIManager
import com.basecode.network.ConstantEnum
import com.basecode.network.RequestListener
import com.basecode.network.RequestMethod
import io.reactivex.disposables.Disposable
import org.json.JSONObject

class PostListViewModel(context: Context) : BaseViewModel(context), RequestListener {

    private lateinit var subscription: Disposable

    private val _registerEvent = MutableLiveData<JSONObject>()

    val registerEvent: LiveData<JSONObject>
        get() = _registerEvent

    init {
        loadPosts(context)
    }

    companion object {
        private val TAG = PostListViewModel::class.java.simpleName
    }

    private fun loadPosts(context: Context) {
        var loginData = LoginData("", "", null, null);
        val apiManager = APIManager(
            context,
            false,
            ConstantEnum.METHOD_LOGIN.toString(),
            this,
            loginData.toGSonString(),
            RequestMethod.POST
        )
        val reqID = apiManager.requestId
        apiManager.perform()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }


    override fun onSuccess(id: Int, response: String) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Log.e(TAG, "Success Response :->$response")
    }

    override fun onError(id: Int, message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun renderView() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class CustomPostViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostListViewModel(context) as T
    }
}
