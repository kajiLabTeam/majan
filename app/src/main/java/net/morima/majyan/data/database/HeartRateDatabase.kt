package net.morima.majyan.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HeartRate::class], version = 1, exportSchema = false)
abstract class HeartRateDatabase : RoomDatabase() {
    abstract fun heartRateDao(): HeartRateDao

    companion object {
        @Volatile
        private var INSTANCE: HeartRateDatabase? = null

        fun getDatabase(context: Context): HeartRateDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HeartRateDatabase::class.java,
                    "heart_rate_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}