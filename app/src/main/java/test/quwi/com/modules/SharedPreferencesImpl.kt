package test.quwi.com.modules

import android.content.SharedPreferences
import test.quwi.com.base.ISharedPreferences

class SharedPreferencesImpl(
    private val preferences: SharedPreferences
) : ISharedPreferences {

    override var accessToken: String?
        get() = getString(ISharedPreferences.ACCESS_TOKEN)
        set(value) {
            save(ISharedPreferences.ACCESS_TOKEN, value)
        }
    override var userId: Long
        get() = getLong(ISharedPreferences.CURRENT_USER_ID)
        set(value) {
            save(ISharedPreferences.CURRENT_USER_ID, value)
        }

    private fun getString(key: String): String? {
        return getString(key, null)
    }

    private fun getString(key: String, defaultValue: String?): String? {
        return preferences.getString(key, defaultValue)
    }

    private fun getLong(key: String, defaultValue: Long): Long {
        return preferences.getLong(key, defaultValue)
    }

    private fun getLong(key: String): Long {
        return preferences.getLong(key, Long.MIN_VALUE)
    }

    private fun save(key: String, value: String?) {
        preferences.edit()
            .putString(key, value)
            .apply()
    }

    private fun save(key: String, value: Long) {
        preferences.edit().putLong(key, value).apply()
    }
}