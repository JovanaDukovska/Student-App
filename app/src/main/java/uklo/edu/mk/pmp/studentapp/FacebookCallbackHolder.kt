package uklo.edu.mk.pmp.studentapp

import com.facebook.CallbackManager

object FacebookCallbackHolder {
    val callbackManager: CallbackManager =
        CallbackManager.Factory.create()
}