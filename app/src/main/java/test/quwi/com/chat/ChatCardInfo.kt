package test.quwi.com.chat

data class ChatCardInfo (
    val type: ChannelTypeEnum,
    val name: String,
    val text: String?,
    val avatarUrl: String?
)