package com.xequal2.launcher.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.xequal2.launcher.domain.AppPrefManager
import com.xequal2.launcher.model.AppItem
import com.xequal2.launcher.util.getAllLaunchableApps

class LauncherViewModel(private val context: Context) : ViewModel() {

    private val _allApps = mutableStateListOf<AppItem>()
    val allApps: List<AppItem> get() = _allApps

    var searchQuery by mutableStateOf("")
    val selectedPackages = mutableStateListOf<String>()

    init {
        loadApps()
    }

    fun loadApps() {
        val apps = getAllLaunchableApps(context)
        _allApps.clear()
        _allApps.addAll(apps)
        selectedPackages.clear()
        selectedPackages.addAll(AppPrefManager.getSelectedApps(context))
    }

    fun isSelected(pkg: String) = selectedPackages.contains(pkg)

    fun toggleSelection(pkg: String, isChecked: Boolean) {
        AppPrefManager.toggleApp(context, pkg, isChecked)
        if (isChecked) selectedPackages.add(pkg)
        else selectedPackages.remove(pkg)
    }

    fun filteredApps(): List<AppItem> {
        return if (searchQuery.isBlank()) allApps
        else allApps.filter { it.appName.contains(searchQuery, ignoreCase = true) }
    }
}
