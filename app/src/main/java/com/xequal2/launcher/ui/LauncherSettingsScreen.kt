package com.xequal2.launcher.ui

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LauncherSettingsScreen() {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("launcher_prefs", Context.MODE_PRIVATE)
    var textState by remember { mutableStateOf(TextFieldValue(prefs.getString("home_text", "") ?: "")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 32.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Launcher Settings",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = textState,
                onValueChange = {
                    textState = it
                    prefs.edit().putString("home_text", it.text).apply()
                },
                label = { Text("Enter text to show on home") },
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val intent = Intent(Settings.ACTION_HOME_SETTINGS).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Open System Launcher Settings")
            }
        }
    }
}