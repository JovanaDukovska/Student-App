package uklo.edu.mk.pmp.studentapp.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F9FF))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            Spacer(modifier = Modifier.height(40.dp))

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