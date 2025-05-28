package com.xequal2.launcher.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.xequal2.launcher.viewmodel.LauncherViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LauncherMainScreen(viewModel: LauncherViewModel) {
    val context = LocalContext.current
    val sheetState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()

    val selectedApps = remember { viewModel.selectedPackages }
    val homeApps = viewModel.allApps.filter { selectedApps.contains(it.packageName) }

    val prefs = context.getSharedPreferences("launcher_prefs", Context.MODE_PRIVATE)
    var homeText by remember { mutableStateOf("") }

    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    LaunchedEffect(currentBackStackEntry.value) {
        homeText = prefs.getString("home_text", "") ?: ""
    }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            BottomSheetScaffold(
                scaffoldState = sheetState,
                sheetContent = {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(0.85f)
                            .background(Color.Black)
                    ) {
                        AppDrawer(viewModel, navController)
                    }
                },
                sheetPeekHeight = 0.dp
            ) {
                Box(modifier = Modifier.fillMaxSize()) {

                    // Text in top-left
                    if (homeText.isNotBlank()) {
                        Text(
                            text = homeText,
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(top = 42.dp, start = 16.dp, end = 16.dp)
                        )
                    }

                    // App grid aligned to bottom
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 100.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(4),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                        ) {
                            items(homeApps) { app ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .clickable {
                                            val launchIntent =
                                                context.packageManager.getLaunchIntentForPackage(app.packageName)
                                            launchIntent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                            launchIntent?.let { context.startActivity(it) }
                                        }
                                ) {
                                    Image(
                                        painter = drawableToPainter(drawable = app.icon),
                                        contentDescription = app.appName,
                                        modifier = Modifier.size(56.dp)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = app.appName,
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }

                    // Swipe-up gesture
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .align(Alignment.BottomCenter)
                            .pointerInput(Unit) {
                                detectVerticalDragGestures { _, dragAmount ->
                                    if (dragAmount < -40) {
                                        coroutineScope.launch {
                                            sheetState.bottomSheetState.expand()
                                        }
                                    }
                                }
                            }
                    )
                }
            }
        }

        composable("settings") {
            LauncherSettingsScreen()
        }
    }
}