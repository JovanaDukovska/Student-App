package uklo.edu.mk.pmp.studentapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyFirebaseMessagingService :
    FirebaseMessagingService() {

    override fun onMessageReceived(
        message: RemoteMessage
    ) {

        val auth =
            FirebaseAuth.getInstance()

        val email =
            auth.currentUser?.email

        if (email != null) {

            val db =
                FirebaseFirestore
                    .getInstance()

            val notificationData =
                hashMapOf(
                    "Title" to
                            (message.notification?.title
                                ?: ""),

                    "Description" to
                            (message.notification?.body
                                ?: "")
                )

            db.collection("students")
                .document(email)
                .collection("notifications")
                .add(notificationData)
        }

        if (Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {

            val channel =
                NotificationChannel(
                    "student_channel",
                    "Student Notifications",
                    NotificationManager
                        .IMPORTANCE_HIGH
                )

            val manager =
                getSystemService(
                    NotificationManager::class.java
                )

            manager.createNotificationChannel(
                channel
            )
        }

        val notification =
            NotificationCompat.Builder(
                this,
                "student_channel"
            )
                .setContentTitle(
                    message.notification?.title
                )
                .setContentText(
                    message.notification?.body
                )
                .setSmallIcon(
                    android.R.drawable
                        .ic_dialog_info
                )
                .build()

        NotificationManagerCompat
            .from(this)
            .notify(1, notification)
    }
}