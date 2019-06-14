package com.basecode.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.basecode.R
import com.basecode.base.BaseAppCompatActivity
import com.basecode.viewmodel.CustomViewModelFactory
import com.basecode.viewmodel.SplashViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : com.basecode.base.BaseAppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
