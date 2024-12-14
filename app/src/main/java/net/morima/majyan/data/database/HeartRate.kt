package net.morima.majyan.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "heart_rate_table")
data class HeartRate(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // 主キー（自動生成）
    val timestamp: Long, // 時刻
    val value: Int // 心拍数
)