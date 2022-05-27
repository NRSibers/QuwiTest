package test.quwi.com.chat

enum class ReadIndicatorEnum(val status: Int) {
    SENT(0),
    READ(1),
    NON(-1);
    companion object {
        private val DEFAULT = SENT
        fun getByValue(value: Int): ReadIndicatorEnum =
            values().find { it.status == value } ?: DEFAULT
    }
}