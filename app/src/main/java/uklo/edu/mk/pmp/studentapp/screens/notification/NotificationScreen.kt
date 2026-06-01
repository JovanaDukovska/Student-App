package uklo.edu.mk.pmp.studentapp.screens.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun NotificationScreen(
    selectedLanguage: String,
    onLanguageChange: (String) -> Unit,
    isGuest: Boolean,
    isGoogleRestricted: Boolean,
    onBackClick: () -> Unit
) {

    var expandedLanguageMenu by remember {
        mutableStateOf(false)
    }

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

        if (
            !isGuest &&
            !isGoogleRestricted &&
            email.isNotEmpty()
        ) {

            db.collection("students")
                .document(email)
                .collection("notifications")
                .get()
                .addOnSuccessListener {

                    notifications =
                        it.documents.map { doc ->
                            doc.data ?: emptyMap()
                        }
                }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F9FF))
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .widthIn(max = 900.dp)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {

                TextButton(
                    onClick = {
                        expandedLanguageMenu =
                            !expandedLanguageMenu
                    }
                ) {
                    Text("🌍 $selectedLanguage")
                }

                DropdownMenu(
                    expanded = expandedLanguageMenu,
                    onDismissRequest = {
                        expandedLanguageMenu = false
                    }
                ) {

                    DropdownMenuItem(
                        text = {
                            Text("MK")
                        },
                        onClick = {
                            onLanguageChange("MK")
                            expandedLanguageMenu = false
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text("EN")
                        },
                        onClick = {
                            onLanguageChange("EN")
                            expandedLanguageMenu = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text =
                    if (selectedLanguage == "MK")
                        "Известувања"
                    else
                        "Notifications",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (isGuest || isGoogleRestricted) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {

                        Text(
                            text =
                                if (selectedLanguage == "MK")
                                    "Ограничен пристап"
                                else
                                    "Restricted Access",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text =
                                if (selectedLanguage == "MK")
                                    "Најавете се со UKLO профил за да ги видите известувањата."
                                else
                                    "Please login with your UKLO account to see notifications."
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            if (!isGuest && !isGoogleRestricted) {

                notifications.forEach { notification ->

                    NotificationCard(
                        selectedLanguage = selectedLanguage,
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
            }
        }

        Button(
            onClick = {
                onBackClick()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(20.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1976D2)
            )
        ) {
            Text(
                if (selectedLanguage == "MK")
                    "⬅ Назад"
                else
                    "⬅ Back"
            )
        }
    }
}

@Composable
fun NotificationCard(
    selectedLanguage: String,
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
                            if (selectedLanguage == "MK")
                                "Прикажи помалку"
                            else
                                "Show Less"
                        else
                            if (selectedLanguage == "MK")
                                "Прикажи повеќе"
                            else
                                "Show More"
                    )
                }
            }
        }
    }
}