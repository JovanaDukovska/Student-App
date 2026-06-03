package uklo.edu.mk.pmp.studentapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        LocalNote::class,
        StudentTask::class
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun localNoteDao(): LocalNoteDao
    abstract fun studentTaskDao(): StudentTaskDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "student_app_database"
                    ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}
