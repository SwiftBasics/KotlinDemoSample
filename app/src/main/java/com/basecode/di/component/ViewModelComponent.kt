package com.basecode.di.component

import com.basecode.di.module.ContextModule
import com.basecode.di.module.NetworkModule
import com.basecode.network.APICallingObservable
import com.basecode.viewmodel.PostListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class), (ContextModule::class)])
interface ViewModelComponent {

    fun inject(postListViewModel: PostListViewModel)
    fun inject(apicall: APICallingObservable)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelComponent
        fun networkModule(networkModule: NetworkModule): Builder
        fun contextModule(contextModule: ContextModule): Builder
    }
}