package com.basecode.model

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.basecode.BR
import com.google.gson.Gson


data class LoginData(
    var _userName: String,
    var _password: String?,
    var _deviceToken: String?,
    var _deviceType: String?
) : Parcelable, BaseObservable() {
    constructor(parcel: Parcel) : this(
        parcel.toString(),
        parcel.toString(),
        parcel.toString(),
        parcel.toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userName)
//        parcel.writeString(password)
//        parcel.writeString(deviceToken)
//        parcel.writeString(deviceType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }

    fun toGSonString(): String {
        return Gson().toJson(this, LoginData::class.java);
    }

    var userName: String
        @Bindable get() = _userName
        set(value) {
            _userName = value
            notifyPropertyChanged(BR.userName)
        }
}