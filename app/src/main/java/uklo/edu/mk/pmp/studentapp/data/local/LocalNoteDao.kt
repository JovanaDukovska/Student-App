package uklo.edu.mk.pmp.studentapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LocalNoteDao {

    @Insert
    suspend fun insertNote(note: LocalNote)

    @Query("SELECT * FROM local_notes ORDER BY id DESC LIMIT 1")
    suspend fun getLastNote(): LocalNote?
}