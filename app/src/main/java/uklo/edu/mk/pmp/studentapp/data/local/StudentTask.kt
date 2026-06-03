package uklo.edu.mk.pmp.studentapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_tasks")
data class StudentTask(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val userEmail: String,

    val taskText: String,

    val completed: Boolean = false
)