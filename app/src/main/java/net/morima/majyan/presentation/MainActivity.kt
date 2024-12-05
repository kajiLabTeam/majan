/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package net.morima.majyan.presentation

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.tooling.preview.devices.WearDevices
import net.morima.majyan.presentation.theme.MajyanTheme
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.mutableStateOf

class MainActivity : ComponentActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var heartRateSensor: Sensor? = null
    private val heartRate = mutableStateOf("N/A") // センサーデータを保持

    override fun onCreate(savedInstanceState: Bundle?) {
        if (checkSelfPermission(android.Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.BODY_SENSORS), 100)
        }

        installSplashScreen()
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)

        setContent {
            WearApp(heartRate.value) // heartRateを渡す
        }
    }

    override fun onResume() {
        super.onResume()
        heartRateSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_HEART_RATE) {
            heartRate.value = event.values.firstOrNull()?.toInt()?.toString() ?: "N/A"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // 必要であれば実装
    }
}


@Composable
fun WearApp(heartRate: String) {
    val navController = rememberNavController()

    MajyanTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            NavGraphWithSwipe(navController = navController, heartRate = heartRate)
        }
    }
}


@Composable
fun SelectionItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
){
    Card(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(painter = rememberVectorPainter(image = icon),  contentDescription = "")
            Spacer(modifier = Modifier.run { width(16.dp) })
            Text(text = title)
        }
    }
}

@Composable
fun NavGraphWithSwipe(navController: NavHostController, heartRate: String) {
    NavHost(
        navController = navController,
        startDestination = "screen_a"
    ) {
        composable("screen_a") {
            ScreenA(navController)
        }
        composable("screen_b") {
            ScreenB(navController, heartRate)
        }
    }
}

@Composable
fun ScreenA(
    navController: NavHostController
){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SelectionItem(
            icon = Icons.Rounded.ShoppingCart,
            title = "東",
            onClick = {
                navController.navigate("screen_b")
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        SelectionItem(
            icon = Icons.Rounded.ShoppingCart,
            title = "南",
            onClick = {
                navController.navigate("screen_b")
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        SelectionItem(
            icon = Icons.Rounded.ShoppingCart,
            title = "西",
            onClick = {
                navController.navigate("screen_b")
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        SelectionItem(
            icon = Icons.Rounded.ShoppingCart,
            title = "北",
            onClick = {
                navController.navigate("screen_b")
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
fun ScreenB(
    navController: NavHostController,
    heartRate: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "心拍数")
        Text(text = " ♡ : $heartRate bpm") // 心拍数を表示

        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Icon(
                painter = rememberVectorPainter(
                    image = Icons.AutoMirrored.Rounded.ArrowBack
                ),
                contentDescription = ""
            )
        }
    }
}


@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp(heartRate = "78") // 仮の心拍数値を渡す
}