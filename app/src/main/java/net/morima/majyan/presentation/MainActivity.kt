/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package net.morima.majyan.presentation

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
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
import androidx.wear.compose.material.TimeText
import androidx.wear.tooling.preview.devices.WearDevices
import net.morima.majyan.R
import net.morima.majyan.presentation.theme.MajyanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {

    val navController = rememberNavController()

    MajyanTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            NavGraphWithSwipe(
                navController = navController
            )
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
fun NavGraphWithSwipe(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "screen_a"
    ) {
        composable("screen_a") {
            ScreenA(navController)
        }
        composable("screen_b") {
            ScreenB(navController)
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
            title = "Screen B",
            onClick = {
                navController.navigate("screen_b")
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        SelectionItem(
            icon = Icons.Rounded.ShoppingCart,
            title = "Screen B",
            onClick = {
                navController.navigate("screen_b")
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        SelectionItem(
            icon = Icons.Rounded.ShoppingCart,
            title = "Screen B",
            onClick = {
                navController.navigate("screen_b")
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        SelectionItem(
            icon = Icons.Rounded.ShoppingCart,
            title = "Screen B",
            onClick = {
                navController.navigate("screen_b")
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        SelectionItem(
            icon = Icons.Rounded.ShoppingCart,
            title = "Screen B",
            onClick = {
                navController.navigate("screen_b")
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
fun ScreenB(
    navController: NavHostController
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = "Screen B")

        Button(
            onClick = {
                navController.popBackStack()
            }
        ){
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
    WearApp()
}