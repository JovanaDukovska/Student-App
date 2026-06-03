package uklo.edu.mk.pmp.studentapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StudentTaskDao {

    @Insert
    suspend fun insertTask(task: StudentTask)

    @Query("SELECT * FROM student_tasks WHERE userEmail = :email")
    suspend fun getTasks(email: String): List<StudentTask>
}