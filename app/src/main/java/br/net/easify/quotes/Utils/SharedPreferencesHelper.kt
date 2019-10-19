package br.net.easify.quotes.Utils

import android.content.Context
import android.preference.PreferenceManager

class SharedPreferencesHelper(context: Context) {

    private val PREF_USER_ID = "usuarioId"

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    fun setUserId(usuarioId: Int) {
        prefs.edit().putString(PREF_USER_ID, usuarioId.toString()).apply()
    }

    fun getUserId() = prefs.getString(PREF_USER_ID, "0")

    fun deleteUserId() {
        prefs.edit().remove(PREF_USER_ID).apply()
    }
}