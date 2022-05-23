package test.quwi.com.auth

import test.quwi.com.auth.response.LoginResponse
import test.quwi.com.base.RequestResult

interface IAuthRepository {

    suspend fun login(
        email: String,
        password: String
    ) : RequestResult<LoginResponse>

    suspend fun signup(
        name: String,
        email: String,
        password: String
    ) : RequestResult<Unit>

    suspend fun logout(anywhere: Boolean) : RequestResult<Unit>
}