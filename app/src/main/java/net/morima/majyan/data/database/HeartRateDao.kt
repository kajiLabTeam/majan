package net.morima.majyan.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HeartRateDao {
    @Insert
    suspend fun insert(heartRate: HeartRate)

    @Query("SELECT * FROM heart_rate_table ORDER BY timestamp DESC")
    fun getAllHeartRates(): Flow<List<HeartRate>>
}