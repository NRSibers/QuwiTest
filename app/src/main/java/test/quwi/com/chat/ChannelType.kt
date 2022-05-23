package test.quwi.com.chat

enum class ChannelTypeEnum(val type: String) {
    PM("pm"),
    UNKNOWN("unknown");

    companion object {
        val DEFAULT = UNKNOWN
        fun getByType(type: String): ChannelTypeEnum =
            values().find { it.type == type } ?: DEFAULT
    }
}