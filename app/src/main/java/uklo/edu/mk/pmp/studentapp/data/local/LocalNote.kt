package uklo.edu.mk.pmp.studentapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_notes")
data class LocalNote(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val note: String
)