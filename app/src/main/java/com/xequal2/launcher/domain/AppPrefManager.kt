package com.xequal2.launcher.domain

import android.content.Context

object AppPrefManager {
    private const val PREF_NAME = "selected_apps"
    private const val KEY_SELECTED = "selected_packages"

    fun getSelectedApps(context: Context): Set<String> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getStringSet(KEY_SELECTED, emptySet()) ?: emptySet()
    }

    fun toggleApp(context: Context, packageName: String, selected: Boolean) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val current = getSelectedApps(context).toMutableSet()
        if (selected) current.add(packageName) else current.remove(packageName)
        prefs.edit().putStringSet(KEY_SELECTED, current).apply()
    }
}
