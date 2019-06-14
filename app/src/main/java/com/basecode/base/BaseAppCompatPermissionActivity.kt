package com.basecode.base

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.basecode.R
import com.basecode.utils.DialogHelper
import java.util.*

/**
 * Base compat activity with permission manager
 * Created by shpatel on 9/5/2016.
 */
class BaseAppCompatPermissionActivity : com.basecode.base.BaseAppCompatActivity() {

    private val KEY_PERMISSION = 200
    private var permissionListener: com.basecode.base.PermissionListener? = null
    private var permissionsAsk: Array<String>? = null
    private var mTag: String = ""

    /**
     * @param context    current Context
     * @param permission String permission to ask
     * @return boolean true/false
     */
    fun isPermissionGranted(context: Context, permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || ContextCompat.checkSelfPermission(
            context,
            permission
        ) === PackageManager.PERMISSION_GRANTED
    }

    /**
     * @param context     current Context
     * @param permissions String[] permission to ask
     * @return boolean true/false
     */
    fun isPermissionsGranted(context: Context, permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true

        var granted = true

        for (permission in permissions) {
            if (!(ActivityCompat.checkSelfPermission(context, permission) === PackageManager.PERMISSION_GRANTED))
                granted = false
        }
        return granted
    }

    private fun internalRequestPermission(permissionAsk: Array<String>) {
        val arrayPermissionNotGranted: Array<String>
        val permissionsNotGranted = ArrayList<String>()

        for (i in permissionAsk.indices) {
            if (!isPermissionGranted(this@BaseAppCompatPermissionActivity, permissionAsk[i])) {
                permissionsNotGranted.add(permissionAsk[i])
            } else {
                if (permissionListener != null) {
                    permissionListener!!.permissionGranted(permissionAsk[i], mTag)
                }
            }
        }

        if (!permissionsNotGranted.isEmpty()) {
            arrayPermissionNotGranted = permissionsNotGranted.toTypedArray()
            ActivityCompat.requestPermissions(
                this@BaseAppCompatPermissionActivity,
                arrayPermissionNotGranted,
                KEY_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {

        if (requestCode != KEY_PERMISSION) {
            return
        }

        val permissionDenied = LinkedList<String>()

        for (i in grantResults.indices) {

            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                permissionDenied.add(permissions[i])
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    permissionListener!!.permissionForeverDenied(permissions[i])
                    return
                } else {
                    permissionListener!!.permissionDenied(permissions[i])
                }
            } else {
                permissionListener!!.permissionGranted(permissions[i], mTag)
            }
        }
    }

    /**
     * @param permission         String permission which needs to be ask
     * @param permissionListener callback PermissionResult
     */
    fun askForPermission(permission: String, permissionListener: com.basecode.base.PermissionListener, tag: String) {
        permissionsAsk = arrayOf(permission)
        this.permissionListener = permissionListener
        this.mTag = tag
        internalRequestPermission(permissionsAsk!!)
    }

    /**
     * @param permissions        String[] permissions which needs to be ask
     * @param permissionListener callback PermissionResult
     */
    fun askForPermissions(permissions: Array<String>, permissionListener: com.basecode.base.PermissionListener, tag: String) {
        permissionsAsk = permissions
        this.permissionListener = permissionListener
        this.mTag = tag
        internalRequestPermission(permissionsAsk!!)
    }

    /**
     * @param context     current context
     * @param denyMessage message display while user deny permission
     */
    fun openSettingsApp(context: Context, denyMessage: String) {

        DialogHelper.showTwoButtonDialog(
            context,
            "",
            denyMessage,
            getString(R.string.settings),
            getString(R.string.cancel),
            DialogInterface.OnClickListener { dialogInterface, i ->
                DialogHelper.dismiss()
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.parse("package:" + context.packageName)
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                    val intent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
                    startActivity(intent)
                }
            },
            DialogInterface.OnClickListener { dialogInterface, i -> DialogHelper.dismiss() },
            false
        )
    }


    fun checkPermission(context: Context, permissionListener: com.basecode.base.PermissionListener) {
        if (isPermissionsGranted(
                context,
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            )
        ) {
            permissionListener.permissionGranted(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, "base_tag")
        } else {
            askForPermissions(
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), permissionListener, "base_tag"
            )
        }
    }
}
