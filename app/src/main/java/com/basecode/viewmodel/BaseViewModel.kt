package com.basecode.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel

abstract class BaseViewModel(context: Context) : ViewModel() {


    init {
        this.renderView();
    }

    abstract fun renderView();


}