package uklo.edu.mk.pmp.studentapp.screens.profile

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
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.analytics.FirebaseAnalytics
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import android.content.Intent
import android.provider.MediaStore
import androidx.compose.material3.OutlinedTextField
import kotlinx.coroutines.launch
import uklo.edu.mk.pmp.studentapp.data.local.AppDatabase
import uklo.edu.mk.pmp.studentapp.data.local.StudentTask
import androidx.compose.runtime.rememberCoroutineScope



@Composable
fun ProfileScreen(
    selectedLanguage: String,
    onLanguageChange: (String) -> Unit,
    isGuest: Boolean,
    isGoogleRestricted: Boolean,
    onBackClick: () -> Unit
) {

    var expandedLanguageMenu by remember {
        mutableStateOf(false)
    }

    var expandedIndexImage by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    val openCamera = {
        val intent =
            Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        context.startActivity(intent)
    }

    var capturedImage by remember {
        mutableStateOf<Bitmap?>(null)
    }

    var expandedTasks by remember {
        mutableStateOf(false)
    }

    val currentEmail =
        FirebaseAuth.getInstance()
            .currentUser?.email ?: ""

    val sharedPreferences =
        context.getSharedPreferences(
            "profile_prefs",
            android.content.Context.MODE_PRIVATE
        )

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(
            sharedPreferences
                .getString(
                    "profile_image_uri_$currentEmail",
                    null
                )
                ?.let { Uri.parse(it) }
        )
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicturePreview()
        ) { bitmap ->

            capturedImage = bitmap
            selectedImageUri = null
        }

    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->

            if (uri != null) {

                selectedImageUri = uri
                capturedImage = null
                sharedPreferences
                    .edit()
                    .putString(
                        "profile_image_uri_$currentEmail",
                        uri.toString()
                    )
                    .apply()
            }
        }

    var fullName by remember { mutableStateOf("") }
    var index by remember { mutableStateOf("") }
    var faculty by remember { mutableStateOf("") }
    var direction by remember { mutableStateOf("") }
    var semester by remember { mutableStateOf(0) }
    var average by remember { mutableStateOf(0.0) }
    var paid by remember { mutableStateOf(false) }
    var semesterType by remember { mutableStateOf("") }
    var tuitionFee by remember { mutableStateOf(0) }
    var email by remember { mutableStateOf("") }

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    val firebaseAnalytics =
        FirebaseAnalytics.getInstance(context)

    val database =
        AppDatabase.getDatabase(context)

    val coroutineScope =
        rememberCoroutineScope()

    var taskText by remember {
        mutableStateOf("")
    }

    var studentTasks by remember {
        mutableStateOf<List<StudentTask>>(emptyList())
    }

    LaunchedEffect(selectedLanguage) {

        val currentEmail =
            auth.currentUser?.email ?: ""

        email = currentEmail

        if (currentEmail.isNotEmpty()) {

            db.collection("students")
                .document(currentEmail)
                .get()
                .addOnSuccessListener { document ->

                    fullName =
                        document.getString("fullName") ?: ""

                    index =
                        document.getString("Index") ?: ""

                    faculty =
                        if (selectedLanguage == "MK")
                            document.getString("FacultyMK")
                                ?: document.getString("Faculty")
                                ?: ""
                        else
                            document.getString("Faculty")
                                ?: document.getString("FacultyMK")
                                ?: ""

                    direction =
                        if (selectedLanguage == "MK")
                            document.getString("DirectionMK")
                                ?: document.getString("Direction")
                                ?: ""
                        else
                            document.getString("Direction")
                                ?: document.getString("DirectionMK")
                                ?: ""

                    semester =
                        document.getLong("Semester")
                            ?.toInt()
                            ?: 0

                    average =
                        document.getDouble("Average")
                            ?: 0.0

                    paid =
                        document.getBoolean("Paid")
                            ?: false

                    semesterType =
                        if (selectedLanguage == "MK")
                            document.getString("semesterTypeMK")
                                ?: document.getString("semesterType")
                                ?: ""
                        else
                            document.getString("semesterType")
                                ?: document.getString("semesterTypeMK")
                                ?: ""

                    tuitionFee =
                        document.getLong("tuitionFee")
                            ?.toInt()
                            ?: document.getDouble("tuitionFee")
                                ?.toInt()
                                    ?: 0
                }
        }
    }

    LaunchedEffect(currentEmail) {

        if (currentEmail.isNotEmpty()) {

            studentTasks =
                database.studentTaskDao()
                    .getTasks(currentEmail)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F9FF))
    )   {

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
                        "Студентски профил"
                    else
                        "Student Profile",
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
                                    "Најавете се за пристап до вашиот профил."
                                else
                                    "Please login to access your profile."
                        )
                    }
                }
            }

            if (!isGuest) {

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(12.dp)
                    ) {

                        Text(
                            if (selectedLanguage == "MK")
                                "👤 Име: $fullName"
                            else
                                "👤 Name: $fullName"
                        )

                        Text(
                            if (selectedLanguage == "MK")
                                "🆔 Индекс: $index"
                            else
                                "🆔 Index: $index"
                        )

                        Text(
                            if (selectedLanguage == "MK")
                                "🎓 Факултет: $faculty"
                            else
                                "🎓 Faculty: $faculty"
                        )

                        Text(
                            if (selectedLanguage == "MK")
                                "💻 Насока: $direction"
                            else
                                "💻 Direction: $direction"
                        )

                        Text(
                            if (selectedLanguage == "MK")
                                "📚 Семестар: $semester"
                            else
                                "📚 Semester: $semester"
                        )

                        Text(
                            if (selectedLanguage == "MK")
                                "📘 Тип на семестар: $semesterType"
                            else
                                "📘 Semester Type: $semesterType"
                        )

                        Text(
                            if (selectedLanguage == "MK")
                                "📈 Просек: $average"
                            else
                                "📈 Average: $average"
                        )

                        Text(
                            if (selectedLanguage == "MK")
                                "💰 Школарина: $tuitionFee денари"
                            else
                                "💰 Tuition Fee: $tuitionFee€"
                        )

                        Text(
                            if (selectedLanguage == "MK")
                                if (paid)
                                    "✅ Платено: Да"
                                else
                                    "❌ Платено: Не"
                            else
                                if (paid)
                                    "✅ Paid: Yes"
                                else
                                    "❌ Paid: No"
                        )

                        Text("📧 Email: $email")
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {

                        Text(
                            text =
                                if (selectedLanguage == "MK")
                                    "📸 Слика од индекс"
                                else
                                    "📸 Image of Index",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        TextButton(
                            onClick = {
                                expandedIndexImage =
                                    !expandedIndexImage
                            }
                        ) {
                            Text(
                                if (expandedIndexImage)
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

                        if (expandedIndexImage) {

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = {
                                    openCamera()
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF1976D2)
                                )
                            ) {
                                Text(
                                    if (selectedLanguage == "MK")
                                        "📷 Сликај индекс"
                                    else
                                        "📷 Take Photo"
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Button(
                                onClick = {
                                    galleryLauncher.launch("image/*")
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF1976D2)
                                )
                            ) {
                                Text(
                                    if (selectedLanguage == "MK")
                                        "🖼️ Избери слика"
                                    else
                                        "🖼️ Choose Photo"
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            capturedImage?.let { bitmap ->

                                Image(
                                    bitmap = bitmap.asImageBitmap(),
                                    contentDescription = "Index Image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(220.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            selectedImageUri?.let { uri ->

                                AsyncImage(
                                    model = uri,
                                    contentDescription = "Index Image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(220.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Text(
                            text =
                                if (selectedLanguage == "MK")
                                    "📋 Студентски задачи"
                                else
                                    "📋 Student Tasks",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        OutlinedTextField(
                            value = taskText,
                            onValueChange = {
                                taskText = it
                            },
                            label = {
                                Text(
                                    if (selectedLanguage == "MK")
                                        "Внеси задача"
                                    else
                                        "Enter task"
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp)
                        )

                        Button(
                            onClick = {
                                if (taskText.isNotBlank()) {
                                    coroutineScope.launch {
                                        database.studentTaskDao().insertTask(
                                            StudentTask(
                                                userEmail = currentEmail,
                                                taskText = taskText,
                                                completed = false
                                            )
                                        )

                                        studentTasks =
                                            database.studentTaskDao()
                                                .getTasks(currentEmail)

                                        taskText = ""
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1976D2)
                            )
                        ) {
                            Text(
                                if (selectedLanguage == "MK")
                                    "Додај задача"
                                else
                                    "Add Task"
                            )
                        }

                        TextButton(
                            onClick = {
                                expandedTasks = !expandedTasks
                            }
                        ) {
                            Text(
                                if (expandedTasks)
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

                        if (expandedTasks) {
                            studentTasks.forEach { task ->

                                Text(
                                    text = "• ${task.taskText}",
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
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