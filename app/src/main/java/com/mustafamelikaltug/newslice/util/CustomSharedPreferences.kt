package com.mustafamelikaltug.newslice.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharedPreferences {
    companion object {
        // Define a key for storing time in SharedPreferences
        private val PREFERENCES_TIME = "preferences_time"

        // Shared Preferences and instance variables
        private var sharedPreferences: SharedPreferences? = null
        @Volatile private var instance: CustomSharedPreferences? = null

        private val lock = Any()

        // Create a singleton instance of CustomSharedPreferences
        operator fun invoke(context: Context): CustomSharedPreferences = instance ?: synchronized(lock) {
            instance ?: makeCustomSharedPreferences(context).also {
                instance = it
            }
        }

        private fun makeCustomSharedPreferences(context: Context): CustomSharedPreferences {
            // Initialize SharedPreferences with the default preferences
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }
    }

    // Function to save a timestamp in SharedPreferences
    fun saveTime(time: Long) {
        sharedPreferences?.edit(commit = true) {
            putLong(PREFERENCES_TIME, time)
        }
    }

    // Function to retrieve the saved timestamp from SharedPreferences
    fun getTime() = sharedPreferences?.getLong(PREFERENCES_TIME, 0)
}
