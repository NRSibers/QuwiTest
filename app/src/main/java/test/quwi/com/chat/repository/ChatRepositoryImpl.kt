package test.quwi.com.chat.repository

import test.quwi.com.chat.response.Channel
import test.quwi.com.auth.response.User
import test.quwi.com.base.RequestResult
import test.quwi.com.base.wrapResult
import test.quwi.com.users.UsersApi

class ChatRepositoryImpl(
    private val chatApi: ChatApi,
    private val usersApi: UsersApi
) : IChatRepository {

    override suspend fun getUsers(ids: List<Long>): RequestResult<List<User>> {
        var idsRow = ""
        ids.forEach {
            idsRow += "$it,"
        }

        idsRow.dropLast(1)

        return wrapResult {
            usersApi.getUsers(idsRow).users
        }
    }

    override suspend fun getChannels(): RequestResult<List<Channel>> {
        return wrapResult {
            chatApi.getChatChannel().channels
        }
    }
}