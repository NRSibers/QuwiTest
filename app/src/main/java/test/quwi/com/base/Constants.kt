package test.quwi.com.base

import test.quwi.com.BuildConfig

object Constants {
    const val SHARED_PREFERENCES = BuildConfig.APPLICATION_ID + ".app_settings"
    const val AUTH_MARKER = "auth"
    const val NO_AUTH_MARKER = "noAuth"
}