package uklo.edu.mk.pmp.studentapp.screens.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.TextButton


@Composable
fun NotificationScreen(
    onBackClick: () -> Unit
) {

    val auth =
        FirebaseAuth.getInstance()

    val db =
        FirebaseFirestore
            .getInstance()

    val email =
        auth.currentUser?.email
            ?: ""

    var notifications by remember {

        mutableStateOf(
            listOf<Map<String, Any>>()
        )
    }

    LaunchedEffect(Unit) {

        db.collection("students")
            .document(email)
            .collection(
                "notifications"
            )
            .get()
            .addOnSuccessListener {

                notifications =
                    it.documents.map { doc ->

                        doc.data
                            ?: emptyMap()
                    }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F9FF))
            .verticalScroll(
                rememberScrollState()
            )
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Notifications",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))
        notifications.forEach { notification ->

            NotificationCard(
                title =
                    notification["Title"]
                        .toString(),

                description =
                    notification["Description"]
                        .toString(),

                example =
                    notification["Example"]
                        ?.toString()
                        ?: ""
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                onBackClick()
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1976D2)
            )
        ) {
            Text("⬅ Back")
        }
    }
}

@Composable
fun NotificationCard(
    title: String,
    description: String,
    example: String = ""
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            6.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = description,
                fontSize = 16.sp
            )

            if (
                expanded &&
                example.isNotEmpty()
            ) {

                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )

                Text(
                    text = example,
                    fontSize = 15.sp
                )
            }

            if (
                example.isNotEmpty()
            ) {

                TextButton(
                    onClick = {
                        expanded =
                            !expanded
                    }
                ) {

                    Text(
                        if (expanded)
                            "Show Less"
                        else
                            "Show More"
                    )
                }
            }
        }
    }
}