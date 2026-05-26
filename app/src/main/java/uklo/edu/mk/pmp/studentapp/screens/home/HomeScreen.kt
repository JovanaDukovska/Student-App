package uklo.edu.mk.pmp.studentapp.screens.home

import uklo.edu.mk.pmp.studentapp.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onProfileClick: () -> Unit,
    onReportClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onLogoutClick: () -> Unit
) {

    var expandedSemester by remember {
        mutableStateOf(false)
    }

    var expandedSubjects by remember {
        mutableStateOf(false)
    }
    var expandedExams by remember {
        mutableStateOf(false)
    }

    var expandedDocuments by remember {
        mutableStateOf(false)
    }

    var expandedPriceList by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F9FF))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 20.dp,
                    top = 20.dp,
                    end = 20.dp,
                    bottom = 120.dp
                )
                )
        {

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(
                        id = R.drawable.uklo_logo
                    ),
                    contentDescription = "UKLO Logo",
                    modifier = Modifier.size(90.dp)
                )

                Image(
                    painter = painterResource(
                        id = R.drawable.fikt_logo
                    ),
                    contentDescription = "FIKT Logo",
                    modifier = Modifier.size(90.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Welcome, Jovana Dukovska",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

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
                        text = "📚 Enrolled Semester",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (expandedSemester) {

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Semester: Winter")
                        Text("Program: INKI")
                        Text("Tuition Fee: 500€")
                        Text("Payment Status: Paid")
                    }

                    TextButton(
                        onClick = {
                            expandedSemester =
                                !expandedSemester
                        }
                    ) {
                        Text(
                            if (expandedSemester)
                                "Show Less"
                            else
                                "Show More"
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
                        text = "💻 Subjects",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    if (expandedSubjects) {

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Algorithms")
                        Text("Semester: 5")
                        Text("ECTS: 6")
                        Text("Attendance: Yes")
                        Text("Professor: Dr. Smith")

                        Spacer(modifier = Modifier.height(12.dp))

                        Divider()

                        Spacer(modifier = Modifier.height(12.dp))

                        Text("Databases")
                        Text("Semester: 5")
                        Text("ECTS: 6")
                        Text("Attendance: Yes")
                        Text("Professor: Dr. Johnson")

                        Spacer(modifier = Modifier.height(12.dp))

                        Divider()

                        Spacer(modifier = Modifier.height(12.dp))

                        Text("Software Engineering")
                        Text("Semester: 5")
                        Text("ECTS: 5")
                        Text("Attendance: No")
                        Text("Professor: Dr. Williams")
                    }

                    TextButton(
                        onClick = {
                            expandedSubjects =
                                !expandedSubjects
                        }
                    ) {
                        Text(
                            if (expandedSubjects)
                                "Show Less"
                            else
                                "Show More"
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
                        text = "✅ Passed Exams",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    if (expandedExams) {

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("1. INKI101 - Programming")
                        Text("Grade: 10")
                        Text("Semester: 1")

                        Spacer(modifier = Modifier.height(12.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(12.dp))

                        Text("2. INKI205 - Databases")
                        Text("Grade: 9")
                        Text("Semester: 3")

                        Spacer(modifier = Modifier.height(12.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(12.dp))

                        Text("3. INKI310 - Algorithms")
                        Text("Grade: 8")
                        Text("Semester: 5")
                    }

                    TextButton(
                        onClick = {
                            expandedExams =
                                !expandedExams
                        }
                    ) {
                        Text(
                            if (expandedExams)
                                "Show Less"
                            else
                                "Show More"
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
                        text = "📄 Documents",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    if (expandedDocuments) {

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("-> Certificate for Regular Student")

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("-> Passed Subjects Certificate")

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("-> Fully Completed Study Program")
                    }

                    TextButton(
                        onClick = {
                            expandedDocuments =
                                !expandedDocuments
                        }
                    ) {
                        Text(
                            if (expandedDocuments)
                                "Show Less"
                            else
                                "Show More"
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
                        text = "💰 Price List",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    if (expandedPriceList) {

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("📚 Penalty Session Exam: 30€")

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("🎓 Semester Fee: 500€")

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("📄 Regular Student Certificate: 3€")

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("📑 Passed Subjects Certificate: 5€")

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("📘 Full Study Program: 10€")
                    }

                    TextButton(
                        onClick = {
                            expandedPriceList =
                                !expandedPriceList
                        }
                    ) {
                        Text(
                            if (expandedPriceList)
                                "Show Less"
                            else
                                "Show More"
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            IconButton(
                onClick = onProfileClick
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile"
                )
            }

            IconButton(
                onClick = onLogoutClick
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Logout"
                )
            }

            IconButton(
                onClick = onReportClick
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Report"
                )
            }

            IconButton(
                onClick = onNotificationClick
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notification"
                )
            }
        }
    }
}