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
import androidx.compose.runtime.LaunchedEffect
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun HomeScreen(
    selectedLanguage: String,
    onLanguageChange: (String) -> Unit,
    isGuest: Boolean,
    isGoogleRestricted: Boolean,
    onProfileClick: () -> Unit,
    onReportClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onLogoutClick: () -> Unit
) {

    var expandedLanguageMenu by remember {
        mutableStateOf(false)
    }

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

    var fullName by remember {
        mutableStateOf("")
    }

    var Semester by remember {
        mutableStateOf(0)
    }

    var Direction by remember {
        mutableStateOf("")
    }

    var Average by remember {
        mutableStateOf(0.0)
    }

    var Paid by remember {
        mutableStateOf(false)
    }

    var index by remember {
        mutableStateOf("")
    }

    var tuitionFee by remember {
        mutableStateOf(0)
    }

    var semesterType by remember {
        mutableStateOf("")
    }

    var subjects by remember {
        mutableStateOf<List<Map<String, Any>>>(
            emptyList()
        )
    }

    var passedExams by remember {
        mutableStateOf<List<Map<String, Any>>>(
            emptyList()
        )
    }

    var documents by remember {
        mutableStateOf<List<Map<String, Any>>>(
            emptyList()
        )
    }

    var priceList by remember {
        mutableStateOf<List<Map<String, Any>>>(
            emptyList()
        )
    }

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(Unit) {

        val currentUser = auth.currentUser

        currentUser?.email?.let { email ->

            db.collection("students")
                .document(email)
                .get()
                .addOnSuccessListener { document ->

                    fullName =
                        document.getString("fullName")
                            ?: ""

                    Semester =
                        document.getLong("Semester")
                            ?.toInt()
                            ?: 0

                    Direction =
                        document.getString("Direction")
                            ?: ""

                    Average =
                        document.getDouble("Average")
                            ?: 0.0

                    Paid =
                        document.getBoolean("Paid")
                            ?: false

                    index =
                        document.getString("index")
                            ?: ""

                    tuitionFee =
                        document.getDouble("tuitionFee")
                            ?.toInt()
                            ?: 0

                    semesterType =
                        document.getString("semesterType")
                            ?: ""

                    db.collection("students")
                        .document(email)
                        .collection("subjects")
                        .get()
                        .addOnSuccessListener { snapshot ->

                            subjects =
                                snapshot.documents.map {
                                    it.data ?: emptyMap()
                                }
                        }

                    db.collection("students")
                        .document(email)
                        .collection("passedExams")
                        .get()
                        .addOnSuccessListener { snapshot ->

                            passedExams =
                                snapshot.documents.map {
                                    it.data ?: emptyMap()
                                }
                        }

                    db.collection("students")
                        .document(email)
                        .collection("documents")
                        .get()
                        .addOnSuccessListener { snapshot ->

                            documents =
                                snapshot.documents.map {
                                    it.data ?: emptyMap()
                                }
                        }

                    db.collection("students")
                        .document(email)
                        .collection("priceList")
                        .get()
                        .addOnSuccessListener { snapshot ->

                            priceList =
                                snapshot.documents.map {
                                    it.data ?: emptyMap()
                                }
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
                    bottom = 120.dp
                )
        )
        {

            Spacer(modifier = Modifier.height(40.dp))

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
                        text = { Text("MK") },
                        onClick = {
                            onLanguageChange("MK")
                            expandedLanguageMenu = false
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("EN") },
                        onClick = {
                            onLanguageChange("EN")
                            expandedLanguageMenu = false
                        }
                    )
                }
            }

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
                text =
                    if (selectedLanguage == "MK")
                        "Добредојде $fullName"
                    else
                        "Welcome $fullName",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            if (isGuest) {

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Card(
                    modifier =
                        Modifier.fillMaxWidth(),
                    shape =
                        RoundedCornerShape(20.dp),
                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color.White
                        )
                ) {

                    Column(
                        modifier =
                            Modifier.padding(20.dp)
                    ) {

                        Text(
                            text = "Guest Mode",
                            fontSize = 22.sp,
                            fontWeight =
                                FontWeight.Bold
                        )

                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )

                        Text(
                            text =
                                if (selectedLanguage == "MK")
                                    "Најавете се за пристап до студентски информации."
                                else
                                    "Login to access student information."
                        )
                    }
                }
            }

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
                        text =
                            if (selectedLanguage == "MK")
                                "📚 Запишан семестар"
                            else
                                "📚 Enrolled Semester",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (expandedSemester) {

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Semester: $semesterType")
                        Text("Semester: ${Semester}")
                        Text("Program: $Direction")
                        Text("Tuition Fee: ${tuitionFee}€")
                        Text(
                            if (Paid)
                                "Payment Status: Paid"
                            else
                                "Payment Status: Not Paid"
                        )
                    }

                    TextButton(
                        onClick = {
                            expandedSemester =
                                !expandedSemester
                        }
                    ) {
                        Text(
                            if (expandedSemester)
                                if (selectedLanguage == "MK") "Прикажи помалку" else "Show Less"
                            else
                                if (selectedLanguage == "MK") "Прикажи повеќе" else "Show More"
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
                        if (selectedLanguage == "MK")
                            "💻 Предмети"
                        else
                            "💻 Subjects",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    if (isGuest) {

                        Card(
                            modifier =
                                Modifier.fillMaxWidth(),
                            shape =
                                RoundedCornerShape(20.dp),
                            colors =
                                CardDefaults.cardColors(
                                    containerColor =
                                        Color.White
                                )
                        ) {

                            Column(
                                modifier =
                                    Modifier.padding(20.dp)
                            ) {

                                Text(
                                    text =
                                        "Guest Mode",
                                    fontSize = 22.sp,
                                    fontWeight =
                                        FontWeight.Bold
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(8.dp)
                                )


                                Text(
                                    if (selectedLanguage == "MK")
                                        "Најаветесе за да видите информации за студентот"
                                    else
                                        "Login to access student information."
                                )
                            }
                        }
                        Spacer(
                            modifier =
                                Modifier.height(16.dp)
                        )
                    }

                    if (expandedSubjects) {

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        subjects.forEach { subject ->

                            Text(
                                text = "Name: " +
                                        (subject["Name"] ?: "")
                            )

                            Text(
                                text = "Semester: " +
                                        (subject["Semester"] ?: "")
                            )

                            Text(
                                text = "ECTS: " +
                                        (subject["EKTS"] ?: "")
                            )

                            Text(
                                text = "Attendance: " +
                                        (subject["Attendance"] ?: "")
                            )

                            Text(
                                text = "Professor: " +
                                        (subject["Profesor"] ?: "")
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            HorizontalDivider()

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )
                        }
                    }

                    TextButton(
                        onClick = {
                            expandedSubjects =
                                !expandedSubjects
                        }
                    ) {

                        Text(
                                if (expandedSemester)
                                    if (selectedLanguage == "MK") "Прикажи помалку" else "Show Less"
                                else
                                    if (selectedLanguage == "MK") "Прикажи повеќе" else "Show More"
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
                        if (selectedLanguage == "MK")
                            "✅ Положени испити"
                        else
                            "✅ Passed Exams",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    if (expandedExams) {

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        passedExams.forEach { exam ->

                            Text(
                                text = "Number: " +
                                        (exam["Number"] ?: "")
                            )

                            Text(
                                text = "Subject Name: " +
                                        (exam["SubjectName"] ?: "")
                            )

                            Text(
                                text = "Grade: " +
                                        (exam["Grade"] ?: "")
                            )

                            Text(
                                text = "Semester: " +
                                        (exam["Semester"] ?: "")
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            HorizontalDivider()

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )
                        }
                    }

                    TextButton(
                        onClick = {
                            expandedExams =
                                !expandedExams
                        }
                    ) {
                        Text(
                            if (expandedSemester)
                                if (selectedLanguage == "MK") "Прикажи помалку" else "Show Less"
                            else
                                if (selectedLanguage == "MK") "Прикажи повеќе" else "Show More"
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
                        if (selectedLanguage == "MK")
                            "📄 Документи"
                        else
                            "📄 Documents",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    if (expandedDocuments) {

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        documents.forEach { document ->

                            Text(
                                text = "📄 " +
                                        (document["Name"] ?: "")
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )
                        }
                    }

                    TextButton(
                        onClick = {
                            expandedDocuments =
                                !expandedDocuments
                        }
                    ) {
                        Text(
                            if (expandedSemester)
                                if (selectedLanguage == "MK") "Прикажи помалку" else "Show Less"
                            else
                                if (selectedLanguage == "MK") "Прикажи повеќе" else "Show More"
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
                        if (selectedLanguage == "MK")
                            "💰 Ценовник"
                        else
                            "💰 Price List",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    if (expandedPriceList) {

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        priceList.forEach { item ->

                            Text(
                                text =
                                    (item["Name"] ?: "")
                                        .toString() +
                                            ": " +
                                            (item["Price"] ?: "") +
                                                "€"
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )
                        }
                    }

                    TextButton(
                        onClick = {
                            expandedPriceList =
                                !expandedPriceList
                        }
                    ) {
                        Text(
                            if (expandedSemester)
                                if (selectedLanguage == "MK") "Прикажи помалку" else "Show Less"
                            else
                                if (selectedLanguage == "MK") "Прикажи повеќе" else "Show More"
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .widthIn(max = 900.dp)
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

            IconButton(
                onClick = onLogoutClick
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Logout"
                )
            }
        }
    }
}