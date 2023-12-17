package com.pbs.expenseApp.ui.screens

import android.content.Context
import android.provider.Settings
import androidx.lifecycle.ViewModel

class SplashViewModel(context: Context): ViewModel() {
    val id: String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}