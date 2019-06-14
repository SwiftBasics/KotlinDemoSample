package com.basecode.utils

import com.basecode.BuildConfig


/**
 * A wrapper for log-output. Useful to disable log output before shipping the application (the debug information can still be collected internally and send to the
 * developer when an error appears)
 *
 */
object Log {

    val VERBOSE = android.util.Log.VERBOSE
    val DEBUG = android.util.Log.DEBUG
    val ERROR = android.util.Log.ERROR
    val INFO = android.util.Log.INFO
    val WARN = android.util.Log.WARN
    val ASSERT = android.util.Log.ASSERT
    var isLogEnabled = BuildConfig.DEBUG
    private var instance: LogInterface? = null
        get() {
            if (field == null) this.instance = newDefaultAndroidLog()
            return field
        }
        set

    private fun newDefaultAndroidLog(): LogInterface {
        return object : LogInterface {

            override fun w(logTag: String, logText: String) {
                android.util.Log.w(logTag, logText)
            }

            override fun w(logTag: String, logText: String, exception: Throwable) {
                android.util.Log.e(logTag, logText, exception)
            }

            override fun v(logTag: String, logText: String) {
                android.util.Log.v(logTag, logText)
            }

            override fun i(logTag: String, logText: String) {
                android.util.Log.i(logTag, logText)
            }

            override fun e(logTag: String, logText: String) {
                android.util.Log.e(logTag, logText)
            }

            override fun e(logTag: String, logText: String, exception: Throwable) {
                android.util.Log.e(logTag, logText, exception)
            }

            override fun d(logTag: String, logText: String) {
                android.util.Log.d(logTag, logText)
            }

        }
    }


    fun e(logTag: String, logText: String) {
        if (isLogEnabled) {
            instance?.e(logTag, logText)
        }
    }

    fun printStackTrace(exception: Throwable) {
        if (isLogEnabled) {
            exception.printStackTrace()
        }
    }

    fun out(message: String) {
        if (isLogEnabled) {
            println(message)
        }
    }

    fun err(message: String) {
        if (isLogEnabled) {
            System.err.println(message)
        }
    }

    interface LogInterface {

        fun d(logTag: String, logText: String)

        fun e(logTag: String, logText: String)

        fun e(logTag: String, logText: String, exception: Throwable)

        fun w(logTag: String, logText: String)

        fun w(logTag: String, logText: String, exception: Throwable)

        fun v(logTag: String, logText: String)

        fun i(logTag: String, logText: String)

    }
}
