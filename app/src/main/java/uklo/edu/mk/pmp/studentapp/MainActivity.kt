package uklo.edu.mk.pmp.studentapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import uklo.edu.mk.pmp.studentapp.navigation.AppNavigation
import uklo.edu.mk.pmp.studentapp.ui.theme.StudentAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StudentAppTheme {
                AppNavigation()
            }
        }
    }
}