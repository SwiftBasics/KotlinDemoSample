package com.basecode.base

import android.app.Application
import com.basecode.di.component.DaggerViewModelComponent
import com.basecode.di.component.ViewModelComponent
import com.basecode.di.module.ContextModule
import com.basecode.di.module.NetworkModule

class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        com.basecode.base.MyApplication.Companion.context = this
        com.basecode.base.MyApplication.Companion.netComponent = DaggerViewModelComponent
            .builder()
            .networkModule(NetworkModule)
            .contextModule(ContextModule(this))
            .build()
    }

    companion object {
        var context: com.basecode.base.MyApplication? = null
            private set
        var netComponent: ViewModelComponent? = null
            private set
    }
}
