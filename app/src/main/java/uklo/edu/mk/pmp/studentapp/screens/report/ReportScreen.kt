package uklo.edu.mk.pmp.studentapp.screens.report

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ReportScreen(
    selectedLanguage: String,
    onLanguageChange: (String) -> Unit,
    isGuest: Boolean,
    isGoogleRestricted: Boolean,
    onBackClick: () -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    var expandedLanguageMenu by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    val email =
        auth.currentUser?.email ?: ""

    var disRating by remember {
        mutableIntStateOf(0)
    }

    var mathRating by remember {
        mutableIntStateOf(0)
    }

    var aldisRating by remember {
        mutableIntStateOf(0)
    }

    var multimediaRating by remember {
        mutableIntStateOf(0)
    }

    var evladaRating by remember {
        mutableIntStateOf(0)
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
                        "Евалуација на предмети"
                    else
                        "Course Evaluation",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            if (isGuest || isGoogleRestricted) {

                Spacer(modifier = Modifier.height(20.dp))

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
                                    "Најавете се со UKLO профил за да оценувате предмети."
                                else
                                    "Please login with your UKLO account to rate courses."
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text =
                            if (selectedLanguage == "MK")
                                "📢 Известување"
                            else
                                "📢 Announcement",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text =
                            if (selectedLanguage == "MK")
                                "Вашите факултетски документи се подготвени."
                            else
                                "Your faculty documents are ready."
                    )

                    if (expanded) {
                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text =
                                if (selectedLanguage == "MK")
                                    "Дипломираните студенти можат да ги подигнат документите од студентската служба."
                                else
                                    "Graduated students can collect their documents from the faculty office."
                        )
                    }

                    TextButton(
                        onClick = {
                            expanded = !expanded
                        }
                    ) {
                        Text(
                            if (expanded)
                                if (selectedLanguage == "MK") "Прикажи помалку" else "Show Less"
                            else
                                if (selectedLanguage == "MK") "Прикажи повеќе" else "Show More"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (!isGuest && !isGoogleRestricted) {

                Text(
                    text =
                        if (selectedLanguage == "MK")
                            "Оценете ги предметите подолу."
                        else
                            "Please evaluate the courses below.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))

                SubjectRatingCard(
                    subject =
                        "Delovni Informaciski Sistemi",
                    selectedRating = disRating,
                    onRatingSelected = {
                        disRating = it
                    }
                )

                SubjectRatingCard(
                    subject =
                        "Matematicko Modeliranje",
                    selectedRating = mathRating,
                    onRatingSelected = {
                        mathRating = it
                    }
                )

                SubjectRatingCard(
                    subject = "ALDIS",
                    selectedRating = aldisRating,
                    onRatingSelected = {
                        aldisRating = it
                    }
                )

                SubjectRatingCard(
                    subject =
                        "Principi na Multimediski Sistemi",
                    selectedRating =
                        multimediaRating,
                    onRatingSelected = {
                        multimediaRating = it
                    }
                )

                SubjectRatingCard(
                    subject = "E-vlada",
                    selectedRating =
                        evladaRating,
                    onRatingSelected = {
                        evladaRating = it
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {

                        db.collection("students")
                            .document(email)
                            .collection("ratings")
                            .document(
                                "Delovni informaciski sistemi"
                            )
                            .update(
                                "Rating",
                                disRating
                            )

                        db.collection("students")
                            .document(email)
                            .collection("ratings")
                            .document(
                                "Matematichko modeliranje"
                            )
                            .update(
                                "Rating",
                                mathRating
                            )

                        db.collection("students")
                            .document(email)
                            .collection("ratings")
                            .document("ALDIS")
                            .update(
                                "Rating",
                                aldisRating
                            )

                        db.collection("students")
                            .document(email)
                            .collection("ratings")
                            .document(
                                "Principi na multimediski sistemi"
                            )
                            .update(
                                "Rating",
                                multimediaRating
                            )

                        db.collection("students")
                            .document(email)
                            .collection("ratings")
                            .document("E-vlada")
                            .update(
                                "Rating",
                                evladaRating
                            )

                        Toast.makeText(
                            context,
                            if (selectedLanguage == "MK")
                                "Евалуацијата е зачувана"
                            else
                                "Evaluation Saved",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1976D2)
                    )
                ) {
                    Text(
                        if (selectedLanguage == "MK")
                            "Зачувај евалуација"
                        else
                            "Save Evaluation"
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
fun SubjectRatingCard(
    subject: String,
    selectedRating: Int,
    onRatingSelected: (Int) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = subject,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                for (rating in 1..5) {

                    FilterChip(
                        selected = selectedRating == rating,
                        onClick = {
                            onRatingSelected(rating)
                        },
                        label = {
                            Text("$rating")
                        }
                    )
                }
            }
        }
    }
}