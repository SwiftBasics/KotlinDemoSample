package com.basecode.network


import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.basecode.R
import com.basecode.base.MyApplication
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.*
import javax.inject.Inject

class APICallingObservable(private val context: Context?, apiname: String?) {
    private var filePath = ""
    private var requestMethod: RequestMethod? = null
    private var params: String? = null
    private val url: String
    /**
     * The Media type.
     */
    internal val MEDIA_TYPE = MediaType.parse("multipart/form-data")

    @Inject
    lateinit var okHttpClient: OkHttpClient
    @Inject
    lateinit var requestBodyBuilder: FormBody.Builder
    @Inject
    lateinit var multiPartrequestBodyBuilder: MultipartBody.Builder
    @Inject
    lateinit var requestBuilder: Request.Builder


    val urlParams: HttpUrl
        get() {
            val httpBuider = HttpUrl.parse(this.url)!!.newBuilder()
            try {
                if (!TextUtils.isEmpty(this.params)) {
                    var jsonObject: JSONObject? = null
                    jsonObject = JSONObject(this.params)
                    val keys = jsonObject.keys()
                    while (keys.hasNext()) {
                        val key = keys.next()
                        httpBuider.addQueryParameter(encode(key), encode(jsonObject.getString(key)))
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return httpBuider.build()
        }

    constructor(context: Context?, apiname: String?, params: String, requestMethod: RequestMethod) : this(
        context,
        apiname
    ) {
        this.requestMethod = requestMethod
        this.params = params
    }

    constructor(context: Context, apiname: String, params: String, filePath: String) : this(context, apiname) {
        this.requestMethod = RequestMethod.MULTIPART
        this.params = params
        this.filePath = filePath
    }

    constructor(context: Context, apiname: String, params: String, filePathList: ArrayList<String>) : this(
        context,
        apiname
    ) {
        this.requestMethod = RequestMethod.MULTIPART
        this.params = params
//        this.filePathList = filePathList
    }

    init {
        //        this.url = "http://52.76.249.114/fermax/ver10/api/web/common/checknewversion";
        this.url = context!!.getString(R.string.server_url) + apiname
        com.basecode.base.MyApplication.netComponent?.inject(this)
        requestMethod = RequestMethod.GET
    }

    fun create(): Observable<*> {
        return Observable.create(ObservableOnSubscribe<String>
        { emitter ->
            try {
                var request: Request?
                var body: RequestBody? = null
                when (requestMethod) {
                    RequestMethod.GET -> request = requestBuilder
                        .url(url)
                        .get()
                        .build()
                    RequestMethod.GET_WITH_PARAMS -> request = requestBuilder!!
                        .url(urlParams)
                        .get()
                        .build()
                    RequestMethod.MULTIPART -> {
                        /*  if (filePathList != null && !filePathList.isEmpty()) {
                              for (i in filePathList.indices) {
                                  multiPartrequestBodyBuilder!!.addFormDataPart(
                                      "fileData",
                                      File(filePathList[i]).name,
                                      RequestBody.create(MEDIA_TYPE, File(filePathList[i]))
                                  )
                              }
                          } else */if (TextUtils.isEmpty(filePath)) {
                            multiPartrequestBodyBuilder!!.addFormDataPart(
                                "fileData",
                                File(this.filePath).name,
                                RequestBody.create(MEDIA_TYPE, File(this.filePath))
                            )
                        }
                        multiPartrequestBodyBuilder!!.addFormDataPart("params", this.params!!)
                        body = multiPartrequestBodyBuilder!!
                            .build()

                        request = requestBuilder!!
                            .url(url)
                            .post(body!!)
                            .build()
                    }
                    else -> {
                        body = requestBodyBuilder!!
//                            .add("params", "{\"version\":\"4.9\",\"deviceType\":\"3\"}")
                            .add("params", this.params!!)
                            .build()
                        request = requestBuilder!!
                            .url(url)
                            .post(body!!)
                            .build()
                    }
                }

                Log.e(TAG, "Request : " + request!!.url())
                Log.e(TAG, "Params : " + this.params)
                Log.e(TAG, "Headers : " + request.headers()!!.toString())
                val response = okHttpClient!!.newCall(request).execute()
                val data = response.body()!!.string()
                if (TextUtils.isEmpty(data)) {
                    emitter.onError(Throwable("Empty response from server"))
                } else {
                    emitter.onNext(data)
                }
            } catch (e: Exception) {
                emitter.onError(e)
                e.printStackTrace()
            } finally {
                emitter.onComplete()
            }
        })
    }

    private fun encode(`val`: String): String {
        try {
            return URLEncoder.encode(`val`, "utf-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return `val`
    }

    companion object {
        private val TAG = APICallingObservable::class.java.simpleName
    }
}
