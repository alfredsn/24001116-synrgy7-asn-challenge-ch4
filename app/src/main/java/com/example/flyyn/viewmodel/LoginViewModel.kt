package com.example.flyyn.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    fun saveLoginData(context: Context, username: String, password: String) {
        val sharedPreferences = context.getSharedPreferences("login_data", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("username", username)
            putString("password", password)
            apply()
        }
    }

    fun validateCredentials(username: String, password: String): Boolean {
        val defaultUsername = "admin"
        val defaultPassword = "admin123"

        return username == defaultUsername && password == defaultPassword
    }
}
