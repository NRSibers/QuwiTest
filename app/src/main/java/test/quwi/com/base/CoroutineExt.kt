package test.quwi.com.base

inline fun <T: Any?> wrapResult(block: () -> T): RequestResult<T> {
    return try {
        RequestResult.Success(block())
    } catch (exception: Exception) {
        RequestResult.Error(exception)
    }
}

