package com.basecode.network

import android.content.Context
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class APIManager {
    private var context: Context? = null
    private var apiname: String? = null
    var requestId = 0
        private set
    private var reqMethod = RequestMethod.GET
    private var params = ""
    private var isProgressVisible = false
    private var listener: RequestListener? = null

    constructor(context: Context, isProgressVisible: Boolean, apiname: String, listener: RequestListener) {
        this.context = context
        this.isProgressVisible = isProgressVisible
        this.apiname = apiname
        this.listener = listener
        reqMethod = RequestMethod.GET
        requestId = UniqueNumberUtils.instance.uniqueId
    }

    constructor(
        context: Context,
        isProgressVisible: Boolean,
        apiname: String,
        listener: RequestListener?,
        params: String,
        reqMethod: RequestMethod
    ) {
        this.context = context
        this.apiname = apiname
        this.isProgressVisible = isProgressVisible
        this.params = params
        this.listener = listener
        this.reqMethod = reqMethod
        requestId = UniqueNumberUtils.instance.uniqueId
    }

    fun perform() {
        val apiCallingObservable = APICallingObservable(this.context, apiname, this.params, reqMethod)
        apiCallingObservable.create()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { disposable ->
                if (isProgressVisible) {
                    // Todo --> Show Dialog progress dialog
                }
            }
            .doOnError { disposable ->
                if (isProgressVisible) {
                    // Todo --> Dismiss Dialog progress dialog
                }
            }
            .doOnNext { disposable ->
                if (isProgressVisible) {
                    // Todo --> Dismiss Dialog progress dialog
                }
            }
            .doOnComplete {
                if (isProgressVisible) {
                    // Todo --> Dismiss Dialog progress dialog
                }
            }
            .subscribe(
                { data ->
                    if (listener != null)
                        listener!!.onSuccess(requestId, data as String)
                },
                { error ->
                    if (listener != null)
                        listener!!.onSuccess(requestId, error.message.toString())
                },{

                }
            )
    }

    companion object {
        private val TAG = APICallingObservable::class.java.simpleName
    }
}
