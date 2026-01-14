package com.sirekanian.spacetime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import com.sirekanian.spacetime.ui.theme.SpacetimeTheme
import com.sirekanian.spacetime.ui.theme.SystemBarColor

private val DarkSystemBarStyle = SystemBarStyle.dark(SystemBarColor.toArgb())

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(DarkSystemBarStyle, DarkSystemBarStyle)
        setContent {
            val presenter = rememberMainPresenter()
            SpacetimeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    MainScreen(presenter)
                }
            }
        }
    }
}
