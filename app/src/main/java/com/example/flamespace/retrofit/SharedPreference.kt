package com.example.flamespace.retrofit

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {

    private const val PREF_NAME = "FlameSpacePrefs"
    private const val KEY_USER_ID = "userId"
    private const val KEY_USERNAME = "username"
    private const val KEY_EMAIL = "email"
    private const val KEY_PHONE_NUMBER = "phoneNumber"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserDetails(context: Context, userId: String, username: String, email: String) {
        val prefs = getPreferences(context)
        prefs.edit().apply {
            putString(KEY_USER_ID, userId)
            putString(KEY_USERNAME, username)
            putString(KEY_EMAIL, email)
            //putString(KEY_PHONE_NUMBER, phoneNumber)
            apply()
        }
    }

    fun getUserId(context: Context): String? {
        return getPreferences(context).getString(KEY_USER_ID, null)
    }

    fun getUsername(context: Context): String? {
        return getPreferences(context).getString(KEY_USERNAME, null)
    }

    fun getEmail(context: Context): String? {
        return getPreferences(context).getString(KEY_EMAIL, null)
    }

    //fun getPhoneNumber(context: Context): String? {
    //    return getPreferences(context).getString(KEY_PHONE_NUMBER, null)
    //}

    fun clearUserData(context: Context) {
        val prefs = getPreferences(context)
        prefs.edit().clear().apply()
    }
}