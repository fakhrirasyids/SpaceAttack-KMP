package com.fakhrirasyids.spaceattack.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fakhrirasyids.spaceattack.SharedRes
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme(this) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    GreetingView(stringResource(resource = SharedRes.strings.app_name))
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text, fontSize = 24.sp, fontFamily = fontFamilyResource(SharedRes.fonts.pressstart2p_regular))
        Image(
            modifier = Modifier
                .width(100.dp)
                .height(100.dp),
            painter = painterResource(imageResource = SharedRes.images.asset_enemy_red_one),
            contentDescription = ""
        )
    }
}

@Preview
@Composable
fun DefaultPreview() {
    val ctx = LocalContext.current
    MyApplicationTheme(ctx) {
        GreetingView("Hello, Android!")
    }
}
