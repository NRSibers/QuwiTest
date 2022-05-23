package test.quwi.com.auth.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val token: String,
    @SerializedName("app_init")
    val appInit: AppInit
)