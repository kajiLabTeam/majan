package net.morima.majyan.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.morima.majyan.data.database.HeartRate
import net.morima.majyan.data.database.HeartRateDatabase
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData

class HeartRateViewModel(application: Application) : AndroidViewModel(application) {

    private val database = HeartRateDatabase.getDatabase(application) // データベースインスタンス取得
    private val heartRateDao = database.heartRateDao()

    val allHeartRates = heartRateDao.getAllHeartRates() // 全ての心拍数データをFlowで取得
    val allHeartRatesLiveData: LiveData<List<HeartRate>> = allHeartRates.asLiveData()

    // 心拍数データを挿入する関数
    fun insertHeartRate(heartRate: HeartRate) {
        viewModelScope.launch {
            heartRateDao.insert(heartRate)
        }
    }
}
