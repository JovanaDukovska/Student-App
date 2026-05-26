package uklo.edu.mk.pmp.studentapp.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uklo.edu.mk.pmp.studentapp.R
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit
) {
    var selectedLanguage by remember {
        mutableStateOf("EN")
    }

    var expandedLanguageMenu by remember {
        mutableStateOf(false)
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 20.dp,
                    end = 20.dp
                ),
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
                        selectedLanguage = "MK"
                        expandedLanguageMenu = false
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text("EN")
                    },
                    onClick = {
                        selectedLanguage = "EN"
                        expandedLanguageMenu = false
                    }
                )
            }
        }

        val context = LocalContext.current
        val auth = FirebaseAuth.getInstance()

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.uklo_logo),
                contentDescription = "UKLO Logo",
                modifier = Modifier.size(90.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.fikt_logo),
                contentDescription = "FIKT Logo",
                modifier = Modifier.size(120.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Student App",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    if (selectedLanguage == "MK")
                        "Е-маил адреса"
                    else
                        "email"
                )
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    if (selectedLanguage == "MK")
                        "Лозинка"
                    else
                        "password"
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                auth.signInWithEmailAndPassword(
                    email,
                    password
                )
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {

                            Toast.makeText(
                                context,
                                "Login successful",
                                Toast.LENGTH_SHORT
                            ).show()

                            onLoginClick()

                        } else {

                            Toast.makeText(
                                context,
                                task.exception?.message
                                    ?: "Login failed",
                                Toast.LENGTH_LONG
                            ).show()
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
                    "Најава"
                else
                    "Login"
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                if (selectedLanguage == "MK")
                    "Најава со Google профил"
                else
                    "Login with Google"
            )
        }

        TextButton(
            onClick = { }
        ) {
            Text(
                if (selectedLanguage == "MK")
                    "Продолжете како гостин"
                else
                    "Continue as Guest"
            )
        }
    }
}