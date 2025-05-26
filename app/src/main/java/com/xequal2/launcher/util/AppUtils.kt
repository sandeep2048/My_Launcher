package com.xequal2.launcher.util

import android.content.Context
import android.content.Intent
import android.content.pm.LauncherApps
import android.content.pm.PackageManager
import android.os.Build
import android.os.UserManager
import com.xequal2.launcher.model.AppItem
fun getAllLaunchableApps(context: Context): List<AppItem> {
    val launcherApps = context.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
    val userManager = context.getSystemService(Context.USER_SERVICE) as UserManager
    val users = userManager.userProfiles

    val appList = mutableListOf<AppItem>()

    for (user in users) {
        val activities = launcherApps.getActivityList(null, user)
        for (activityInfo in activities) {
            val appName = activityInfo.label.toString()
            val packageName = activityInfo.componentName.packageName
            val icon = activityInfo.getBadgedIcon(0)

            appList.add(AppItem(appName, packageName, icon))
        }
    }

    return appList.sortedBy { it.appName.lowercase() }
}

