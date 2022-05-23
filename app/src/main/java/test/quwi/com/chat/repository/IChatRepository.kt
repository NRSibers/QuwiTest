package test.quwi.com.chat.repository

import test.quwi.com.chat.response.Channel
import test.quwi.com.auth.response.User
import test.quwi.com.base.RequestResult

interface IChatRepository {
    suspend fun getUsers(ids: List<Long>): RequestResult<List<User>>
    suspend fun getChannels(): RequestResult<List<Channel>>
}