package com.home.momentousmovies.data.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.home.momentousmovies.utils.Constants.NAME_PREF

class AppPreferenceHelper (context: Context) : PreferenceHelper {
    companion object {
        private const val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
    }

    private val mPrefs: SharedPreferences = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE)

    override fun getAccessToken(): String? = mPrefs.getString(PREF_KEY_ACCESS_TOKEN, "")
    override fun setAccessToken(accessToken: String?) = mPrefs.edit { putString(PREF_KEY_ACCESS_TOKEN, accessToken) }

}