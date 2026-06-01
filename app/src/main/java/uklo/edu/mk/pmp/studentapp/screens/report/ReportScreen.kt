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

data class RatingCourse(
    val documentId: String,
    val name: String
)

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

    var ratingCourses by remember {
        mutableStateOf<List<RatingCourse>>(
            emptyList()
        )
    }

    val selectedRatings =
        remember {
            mutableStateMapOf<String, Int>()
        }

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    val email =
        auth.currentUser?.email ?: ""

    LaunchedEffect(email) {

        if (!isGuest && email.isNotEmpty()) {

            db.collection("students")
                .document(email)
                .collection("ratings")
                .get()
                .addOnSuccessListener { snapshot ->

                    ratingCourses =
                        snapshot.documents.map { doc ->

                            selectedRatings[doc.id] =
                                doc.getLong("Rating")
                                    ?.toInt()
                                    ?: 0

                            RatingCourse(
                                documentId = doc.id,
                                name =
                                    doc.getString("Name")
                                        ?: doc.id
                            )
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
                .align(Alignment.TopCenter)
                .fillMaxHeight()
                .widthIn(max = 900.dp)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(
                    start = 20.dp,
                    top = 20.dp,
                    end = 20.dp,
                    bottom = 100.dp
                ),
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

            if (isGuest) {

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
                                    "Најавете се за да оценувате предмети."
                                else
                                    "Please login to rate courses."
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

            Spacer(modifier = Modifier.height(16.dp))

            if (!isGuest) {

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

                ratingCourses.forEach { course ->

                    SubjectRatingCard(
                        subject = course.name,
                        selectedRating =
                            selectedRatings[course.documentId] ?: 0,
                        onRatingSelected = {
                            selectedRatings[course.documentId] = it
                        }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {

                        ratingCourses.forEach { course ->

                            db.collection("students")
                                .document(email)
                                .collection("ratings")
                                .document(course.documentId)
                                .update(
                                    "Rating",
                                    selectedRatings[course.documentId] ?: 0
                                )
                        }

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
                .widthIn(max = 900.dp)
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
                        selected =
                            selectedRating == rating,
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