package uklo.edu.mk.pmp.studentapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.messaging.FirebaseMessaging
import uklo.edu.mk.pmp.studentapp.navigation.AppNavigation
import uklo.edu.mk.pmp.studentapp.ui.theme.StudentAppTheme
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import android.content.Intent


class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(application)

        FirebaseMessaging
            .getInstance()
            .token
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    Log.d(
                        "FCM_TOKEN",
                        task.result
                    )
                }
            }
        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission
                    .POST_NOTIFICATIONS
            ) != PackageManager
                .PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission
                        .POST_NOTIFICATIONS
                ),
                100
            )
        }

        setContent {
            StudentAppTheme {
                AppNavigation()
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        FacebookCallbackHolder
            .callbackManager
            .onActivityResult(
                requestCode,
                resultCode,
                data
            )
    }
}