package test.quwi.com.chat.response

import com.google.gson.annotations.SerializedName

data class Channel(
    val id: String,
    val type: String,
    @SerializedName("dta_create")
    val dtaCreate: String,
    @SerializedName("id_users")
    val idUsers: List<Long>,
    @SerializedName("id_project")
    val idProject: Long?,
    @SerializedName("id_partner")
    val idPartner: Long,
    @SerializedName("dta_last_read")
    val dtaLastRead: String,
    @SerializedName("is_unread_manual")
    val isUnreadManual: Boolean,
    @SerializedName("dta_change_msg")
    val dtaChangeMsg: String,
    @SerializedName("message_last")
    val messageLast: MessageLast?
) {
    data class MessageLast(
        val id: String,
        @SerializedName("id_user")
        val idUser: Long,
        @SerializedName("dta_create")
        val dtaCreate: String,
        @SerializedName("is_read")
        val isRead: Int,
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
