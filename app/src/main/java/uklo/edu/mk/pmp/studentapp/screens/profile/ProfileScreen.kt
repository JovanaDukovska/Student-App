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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F9FF))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
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
                                    "Најавете се со UKLO профил за пристап до вашиот профил."
                                else
                                    "Please login with your UKLO account to access your profile."
                        )
                    }
                }
            }

            if (!isGuest && !isGoogleRestricted) {

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
                                "👤 Име: Јована Дуковска"
                            else
                                "👤 Name: Jovana Dukovska"
                        )

                        Text(
                            if (selectedLanguage == "MK")
                                "🆔 Индекс: INKI930"
                            else
                                "🆔 Index Number: INKI930"
                        )

                        Text(
                            if (selectedLanguage == "MK")
                                "🎓 Факултет: ФИКТ"
                            else
                                "🎓 Faculty: FIKT"
                        )

                        Text(
                            if (selectedLanguage == "MK")
                                "💻 Студиска програма: Софтверско инженерство"
                            else
                                "💻 Study Program: Software Engineering"
                        )

                        Text(
                            if (selectedLanguage == "MK")
                                "📚 Семестар: 6"
                            else
                                "📚 Semester: 6"
                        )

                        Text("⭐ ECTS: 180")

                        Text(
                            if (selectedLanguage == "MK")
                                "📈 Просечна оценка: 9.2"
                            else
                                "📈 Average Grade: 9.2"
                        )

                        Text(
                            if (selectedLanguage == "MK")
                                "✅ Статус: Активен студент"
                            else
                                "✅ Student Status: Active"
                        )

                        Text(
                            if (selectedLanguage == "MK")
                                "📅 Академска година: 2025/2026"
                            else
                                "📅 Academic Year: 2025/2026"
                        )

                        Text("📧 Email: student@uklo.edu.mk")
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