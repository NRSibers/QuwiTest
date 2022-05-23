package test.quwi.com.auth

import test.quwi.com.auth.request.LoginRequestBody
import test.quwi.com.auth.request.LogoutRequestBody
import test.quwi.com.auth.request.SignupRequestBody
import test.quwi.com.auth.response.LoginResponse
import test.quwi.com.base.RequestResult
import test.quwi.com.base.wrapResult

class AuthRepositoryImpl(
    private val authApi: AuthApi) : IAuthRepository {

    companion object {
        private const val DEVICE = "android"
        private const val TIMEZONE = "Europe/Helsinki"
    }

    override suspend fun login(
        email: String,
        password: String
    ) : RequestResult<LoginResponse> {
        return wrapResult {
            authApi.login(
                DEVICE,
                TIMEZONE,
                LoginRequestBody( email, password)
            )
        }
    }

    override suspend fun signup(
        name: String,
        email: String,
        password: String
    ) : RequestResult<Unit> {
        return wrapResult {
            authApi.signup(
                DEVICE,
                TIMEZONE,
                SignupRequestBody(
                    name,
                    email,
                    password
                )
            )
        }
    }

    override suspend fun logout(anywhere: Boolean) : RequestResult<Unit> {
        return wrapResult {
            authApi.logout(LogoutRequestBody(anywhere))
        }
    }
}