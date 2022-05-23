package test.quwi.com.chat

import retrofit2.http.GET
import test.quwi.com.chat.response.ChatChannelsResponse

interface ChatApi {
    @GET("chat-channels/")
    suspend fun getChatChannel() : ChatChannelsResponse
}