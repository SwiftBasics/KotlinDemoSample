package com.basecode.ui.activities

import android.content.Context
import android.os.Bundle
import com.basecode.R
import com.basecode.base.BaseAppCompatActivity
import com.basecode.model.LoginData
import com.basecode.network.APIManager
import com.basecode.network.ConstantEnum
import com.basecode.network.RequestListener
import com.basecode.network.RequestMethod

class SplashActivity : com.basecode.base.BaseAppCompatActivity(), RequestListener {
    override fun onSuccess(id: Int, response: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(id: Int, message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    private fun loadPosts(context: Context) {
        var loginData = LoginData("3261", "+913261000000", null, null);
        val apiManager = APIManager(
            context,
            false,
            ConstantEnum.METHOD_LOGIN.toString(),
            this,
            loginData.toGSonString(),
            RequestMethod.POST
        )
        val reqID = apiManager.requestId
        apiManager.perform()
    }

}
