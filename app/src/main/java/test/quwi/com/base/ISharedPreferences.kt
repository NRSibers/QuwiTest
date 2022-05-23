package test.quwi.com.base

import test.quwi.com.auth.response.User

interface ISharedPreferences {
    companion object {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val CURRENT_USER_ID = "CURRENT_USER_ID"
    }

    var accessToken: String?

    var userId: Long
}