package com.basecode.network

public enum class ConstantEnum(s: String) {

    METHOD_LOGIN ( "user/login"),
    METHOD_LOGINDETAIL ("user/logindetail"),

    KEY_PARAMS("params"),
    KEY_HEADERTOKEN("headertoken"),
    KEY_LANGUAGE("language"),
    KEY_DEVICEVERSION("deviceversion"),
    KEY_DEVICETYPE("deviceType"),

    ;

    var enumName: String = s;

    override fun toString(): String {
        return enumName
    }

    fun toLowerCase(): String {
        return this.enumName.toLowerCase()
    }
}