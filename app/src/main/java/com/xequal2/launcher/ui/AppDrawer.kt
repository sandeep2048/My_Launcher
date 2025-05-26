package com.xequal2.launcher.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import com.xequal2.launcher.viewmodel.LauncherViewModel

@Composable
fun AppDrawer(viewModel: LauncherViewModel, navController: NavHostController) {
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = viewModel.searchQuery,
                onValueChange = { viewModel.searchQuery = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Search apps...") }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = Color.White,
                modifier = Modifier.clickable { navController.navigate("settings") }
            )
        }

        LazyColumn {
            items(viewModel.filteredApps().size) { pos ->
                val app = viewModel.filteredApps()[pos]
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = context.packageManager.getLaunchIntentForPackage(app.packageName)
                            intent?.let {
                                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                context.startActivity(it)
                            }
                        }
                        .padding(vertical = 8.dp)
                ) {
                    Image(
                        painter = drawableToPainter(app.icon),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))
                    Text(modifier = Modifier.weight(1f), text = app.appName, color = Color.White)
                    Checkbox(
                        checked = viewModel.isSelected(app.packageName),
                        onCheckedChange = {
                            viewModel.toggleSelection(app.packageName, it)
                        }
                    )
                }
            }
        }
    }
}



@Composable
fun drawableToPainter(drawable: Drawable): Painter {
    val bitmap = drawable.toBitmap()
    return BitmapPainter(bitmap.asImageBitmap())
}