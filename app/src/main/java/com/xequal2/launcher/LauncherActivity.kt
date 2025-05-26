package com.xequal2.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.xequal2.launcher.ui.LauncherMainScreen
import com.xequal2.launcher.viewmodel.LauncherViewModel

class LauncherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.Black.toArgb()
        window.navigationBarColor = Color.Black.toArgb()
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    background = Color.Black,
                    surface = Color.Black,
                    onBackground = Color.White,
                    onSurface = Color.White
                )
            ) {
                val viewModel = remember { LauncherViewModel(applicationContext) }
                  LauncherMainScreen(viewModel)
            }
        }
    }
}