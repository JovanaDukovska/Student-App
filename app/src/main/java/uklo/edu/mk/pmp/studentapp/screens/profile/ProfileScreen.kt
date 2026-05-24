package uklo.edu.mk.pmp.studentapp.screens.profile

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

@Composable
fun ProfileScreen(
    onBackClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F9FF))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Student Profile",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

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

                Text("👤 Name: Jovana Dukovska")
                Text("🆔 Index Number: INKI930")
                Text("🎓 Faculty: FIKT")
                Text("💻 Study Program: Software Engineering")
                Text("📚 Semester: 6")
                Text("⭐ ECTS: 180")
                Text("📈 Average Grade: 9.2")
                Text("✅ Student Status: Active")
                Text("📅 Academic Year: 2025/2026")
                Text("📧 Email: student@uklo.edu.mk")
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                onBackClick()
            },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1976D2)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("⬅ Back")
        }
    }
}