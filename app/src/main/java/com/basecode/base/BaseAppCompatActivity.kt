/*
 * Copyright (c) 2015. Created by Mayur Bhola
 */

package com.basecode.base


import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.basecode.R
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Base class for ActionBarActivity with generalise methods
 */

open class BaseAppCompatActivity : AppCompatActivity() {
    var actionBar: ActionBar? = null
    var prefManager: SharedPreferences? = null
    private var tvTitle: TextView? = null
    private var fmMenu: FrameLayout? = null
    private var txtMenu: TextView? = null
    private var tvCount: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT < 26) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        }
        actionBar = getSupportActionBar()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
        // need it for font implementation
    }

    override fun setTitle(titleId: Int) {
        if (tvTitle != null) {
            tvTitle!!.setText(titleId)
        }
        super.setTitle(titleId)
    }

    override fun setTitle(title: CharSequence) {
        if (tvTitle != null) {
            tvTitle!!.text = title
        }
        super.setTitle(title)
    }

    fun initActionBar(title: String, showHome: Boolean) {

        try {

            //          initializeFragmentManager actionbar
            actionBar = getSupportActionBar()
            if (actionBar != null) {
//                setHomeEnable(R.drawable.ic_home_white)
                if (!TextUtils.isEmpty(title)) {
                    setTitle(title.toUpperCase())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun setHomeEnable(enable: Boolean) {
        if (actionBar != null) {
            actionBar!!.setHomeButtonEnabled(enable)
            actionBar!!.setDisplayShowTitleEnabled(false)
//            actionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_white)
            actionBar!!.setDisplayShowCustomEnabled(true)
            actionBar!!.setDisplayHomeAsUpEnabled(enable)
            actionBar!!.setDisplayShowHomeEnabled(enable)
        }
    }


    fun setHomeEnable(buttonId: Int) {
        if (actionBar != null) {
            actionBar!!.setHomeButtonEnabled(true)
            actionBar!!.setDisplayShowTitleEnabled(false)
            actionBar!!.setHomeAsUpIndicator(buttonId)
            actionBar!!.setDisplayShowCustomEnabled(true)
            actionBar!!.setDisplayHomeAsUpEnabled(true)
            actionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

}
