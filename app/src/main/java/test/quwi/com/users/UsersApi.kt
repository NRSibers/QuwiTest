package test.quwi.com.users

import retrofit2.http.GET
import retrofit2.http.Query
import test.quwi.com.auth.response.UsersResponse

interface UsersApi {
    @GET("users/foreign")
    suspend fun getUsers(
        @Query("ids") ids: String
    ) : UsersResponse
}