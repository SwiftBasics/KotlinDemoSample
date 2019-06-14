package com.basecode.utils

import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.basecode.R


object DialogHelper {

    private var dialogred: AlertDialog? = null

    fun showMessage(context: Context, title: String, message: String) {

        val builder = AlertDialog.Builder(context)
        if (!TextUtils.isEmpty(title))
            builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(true)
        builder.setPositiveButton(
            R.string.ok,
            DialogInterface.OnClickListener { dialog, id -> })
        val dialogred = builder.create()
        dialogred.show()
        val b = dialogred.getButton(DialogInterface.BUTTON_NEGATIVE)
        val a = dialogred.getButton(DialogInterface.BUTTON_POSITIVE)
        if (b != null)
            b!!.setTextColor(ContextCompat.getColor(context, R.color.red))
        if (a != null)
            a!!.setTextColor(ContextCompat.getColor(context, R.color.yellow))
    }

    fun showTwoButtonDialog(
        context: Context, title: String, message: String,
        possitiveText: String, negetiveText: String,
        possitiveListener: DialogInterface.OnClickListener,
        negetiveListener: DialogInterface.OnClickListener, cancelable: Boolean
    ) {
        val builder = AlertDialog.Builder(context)
        if (!TextUtils.isEmpty(title))
            builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(cancelable)
        builder.setPositiveButton(possitiveText, possitiveListener)
        builder.setNegativeButton(negetiveText, negetiveListener)

        dialogred = builder.create()
        dialogred!!.show()
        val b = dialogred!!.getButton(DialogInterface.BUTTON_NEGATIVE)
        val a = dialogred!!.getButton(DialogInterface.BUTTON_POSITIVE)
        if (b != null) {
            b!!.setTextColor(ContextCompat.getColor(context, R.color.red))
        }
        if (a != null) {
            a!!.setTextColor(ContextCompat.getColor(context, R.color.yellow))
        }
    }

    fun showTwoButtonDialog(
        context: Context, title: String, message: String,
        possitiveText: String, negetiveText: String,
        possitiveListener: View.OnClickListener,
        negetiveListener: View.OnClickListener, cancelable: Boolean
    ) {
        val builder = AlertDialog.Builder(context)
        if (!TextUtils.isEmpty(title))
            builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(cancelable)
        builder.setPositiveButton(possitiveText, DialogInterface.OnClickListener { dialogInterface, i -> })
        builder.setNegativeButton(negetiveText, DialogInterface.OnClickListener { dialogInterface, i -> })

        dialogred = builder.create()
        dialogred!!.show()
        val b = dialogred!!.getButton(DialogInterface.BUTTON_NEGATIVE)
        val a = dialogred!!.getButton(DialogInterface.BUTTON_POSITIVE)
        if (b != null) {
            b!!.setTextColor(ContextCompat.getColor(context, R.color.red))
            b!!.setOnClickListener(negetiveListener)
        }

        if (a != null) {
            a!!.setTextColor(ContextCompat.getColor(context, R.color.yellow))
            a!!.setOnClickListener(possitiveListener)
        }
    }

    fun dismiss() {
        if (dialogred != null)
            dialogred!!.dismiss()
    }

    fun showOkButtonDialog(
        context: Context, title: String, message: String,
        okText: String,
        possitiveListener: DialogInterface.OnClickListener,
        cancelable: Boolean
    ) {
        val builder = AlertDialog.Builder(context)
        if (!TextUtils.isEmpty(title))
            builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(cancelable)
        builder.setPositiveButton(okText, possitiveListener)

        val dialogred = builder.create()
        dialogred.show()
        val b = dialogred.getButton(DialogInterface.BUTTON_NEGATIVE)
        val a = dialogred.getButton(DialogInterface.BUTTON_POSITIVE)
        if (b != null)
            b!!.setTextColor(ContextCompat.getColor(context, R.color.red))
        if (a != null)
            a!!.setTextColor(ContextCompat.getColor(context, R.color.yellow))
    }


}
