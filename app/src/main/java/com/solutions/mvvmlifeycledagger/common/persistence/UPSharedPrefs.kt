package co.realpost.android.common.persistance

import android.content.Context
import android.content.SharedPreferences

class UPSharedPrefs(val context: Context) {

    companion object {
        const val FILE_PERMANENT_PREFS = "permanent_pref"
        const val FILE_USER_PREFS = "user_pref"

    }

    private val permanentSharedPreferences: SharedPreferences =
            context.getSharedPreferences(FILE_PERMANENT_PREFS, Context.MODE_PRIVATE)

    private val userSharedPreferences: SharedPreferences =
            context.getSharedPreferences(FILE_USER_PREFS, Context.MODE_PRIVATE)

    private fun getBooleanValue(key: String, prefs: SharedPreferences): Boolean {
        return prefs.getBoolean(key, false)
    }

    private fun setBooleanValue(key: String, value: Boolean, prefs: SharedPreferences) {
        prefs.edit()
                .putBoolean(key, value)
                .apply()
    }

    private fun getStringValue(key: String, prefs: SharedPreferences): String? {
        return prefs.getString(key, null)
    }

    private fun setStringValue(key: String, value: String, prefs: SharedPreferences) {
        prefs.edit()
                .putString(key, value)
                .apply()
    }

    fun getUserPrefsBooleanValue(key: String): Boolean {
        return getBooleanValue(key, userSharedPreferences)
    }

    fun setUserPrefsBooleanValue(key: String, value: Boolean) {
        setBooleanValue(key, value, userSharedPreferences)
    }

    fun getUserPrefsStringValue(key: String): String? {
        return getStringValue(key, userSharedPreferences)
    }

    fun setUserPrefsStringValue(key: String, value: String) {
        setStringValue(key, value, userSharedPreferences)
    }

    fun getPermanentPrefsBooleanValue(key: String): Boolean {
        return getBooleanValue(key, permanentSharedPreferences)
    }

    fun setPermanentPrefsBooleanValue(key: String, value: Boolean) {
        setBooleanValue(key, value, permanentSharedPreferences)
    }

    fun getPermanentPrefsStringValue(key: String): String? {
        return getStringValue(key, permanentSharedPreferences)
    }

    fun setPermanentPrefsStringValue(key: String, value: String) {
        setStringValue(key, value, permanentSharedPreferences)
    }

    fun clearUserPrefs() {
        userSharedPreferences.edit()
                .clear()
                .apply()
    }
}