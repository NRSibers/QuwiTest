package test.quwi.com.chat.response

import com.google.gson.annotations.SerializedName

data class Channel(
    val id: String,
    val type: String,
    @SerializedName("dta_create")
    val dtaCreate: String,
    @SerializedName("id_users")
    val idUsers: List<Long>,
    @SerializedName("message_last")
    val messageLast: MessageLast?
) {
    data class MessageLast(
        val id: String,
        @SerializedName("dta_create")
        val dtaCreate: String,
        val user: User,
        val text: String?,
        val snippet: String?,
    ) {
        data class User(
            val name: String,
            @SerializedName("avatar_url")
            val avatarUrl: String?
        )
    }
}
