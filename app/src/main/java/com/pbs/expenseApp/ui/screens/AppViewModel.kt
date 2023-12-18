package com.pbs.expenseApp.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.currentBackStackEntryAsState

class AppViewModel(context: Context): ViewModel() {
    @SuppressLint("HardwareIds")
    val id: String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}
