package test.quwi.com.auth

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import test.quwi.com.auth.request.LoginRequestBody
import test.quwi.com.auth.request.LogoutRequestBody
import test.quwi.com.auth.request.SignupRequestBody
import test.quwi.com.auth.response.LoginResponse

interface AuthApi {
    @POST("auth/signup/")
    suspend fun signup(
        @Header("client_device") clientDevice: String,
        @Header("client_timezone_name") clientTimezoneName: String,
        @Body signUpRequestBody: SignupRequestBody
    ) : LoginResponse

    @POST("auth/login")
    suspend fun login(
        @Header("client_device") clientDevice: String,
        @Header("client_timezone_name") clientTimezoneName: String,
        @Body loginRequestBody: LoginRequestBody
    ) : LoginResponse

    @POST("auth/logout")
    suspend fun logout(@Body logoutRequestBody: LogoutRequestBody)
}