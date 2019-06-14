package com.basecode.di.module

import android.content.Context
import android.content.SharedPreferences
import com.basecode.BuildConfig
import com.basecode.R
import com.basecode.network.Api
import com.basecode.network.ConstantEnum
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.*
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = arrayOf(ContextModule::class))
@Suppress("unused")
object NetworkModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun providePostApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    fun getSharedPref(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    }

    @Provides
    fun headersInterceptor(pref: SharedPreferences) = Interceptor { chain ->
        chain.proceed(
            chain.request().newBuilder()
                .addHeader("deviceToken", "")
                .addHeader("deviceType", "3")
                .addHeader("version", "9")
                .addHeader("headertoken", "")
                .addHeader("userId", "")
                .addHeader("issecuritydisable", "1")
                .addHeader("companyid", "3261001819")
                .build()
        )

    }

    @Provides
    @Singleton
    internal fun provideOkhttpClient(pref: SharedPreferences): OkHttpClient {
        val certificatePinner = CertificatePinner.Builder()
            //TODO--For Release
            .add("app.iplusbyfermax.com", "sha256/lJqgaL1BxI+pFO2Jc3Q+vMPT0wHSFWl3OROLy1Ra1jE=")
            .add("app.iplusbyfermax.com", "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
            .add("app.iplusbyfermax.com", "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
            .add("app.iplusbyfermax.com", "sha256/KwccWaCgrnaw6tsrrSO61FgLacNgG2MMLq8GE6+oP5I=")
            .build()
        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .addInterceptor(headersInterceptor(pref))
            .build()

        return client;
    }

    @Provides
    @Singleton
    internal fun getHeaders(pref: SharedPreferences): Headers {
        return Headers.Builder()
            .add(ConstantEnum.KEY_HEADERTOKEN.toString(), "")
            .add(ConstantEnum.KEY_LANGUAGE.toString(), "")
            .add(ConstantEnum.KEY_DEVICEVERSION.toString(), BuildConfig.VERSION_NAME)
            .add(ConstantEnum.KEY_DEVICETYPE.toString(),"3")
            .add("issecuritydisable", "1")
            .build()
    }

    @Provides
    @Singleton
    internal fun getOkHttpRequestBuilder(headers: Headers): Request.Builder {
        val builder = Request.Builder()
        builder.headers(headers)
        return builder
    }

    @Provides
    @Singleton
    internal fun getRequestBody(): FormBody.Builder {
        return FormBody.Builder()
    }

    @Provides
    @Singleton
    internal fun getMultiPartRequestBody(): MultipartBody.Builder {
        return MultipartBody.Builder()
    }

}