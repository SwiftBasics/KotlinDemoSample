package com.basecode.base

/**
 * Callback for permission result
 * Created by shpatel on 9/5/2016.
 */
interface PermissionListener {

    fun permissionGranted(permission: String, tag: String)

    fun permissionDenied(permission: String)

    fun permissionForeverDenied(permission: String)
}
