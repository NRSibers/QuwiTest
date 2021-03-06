package test.quwi.com.chat

data class ChatCardInfo (
    val type: ChannelTypeEnum,
    val name: String,
    val text: String?,
    val avatarUrl: String?,
    val isSelfMessage: Boolean = false,
    val isSavedChannel: Boolean = false,
    val pinToTop: Boolean = false,
    val readIndicator: ReadIndicatorEnum = ReadIndicatorEnum.NON,
    val dateText: String = ""
)