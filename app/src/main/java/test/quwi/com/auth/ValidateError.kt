package test.quwi.com.auth

sealed class ValidateError {
    data class NoFirstName(val hasError: Boolean) : ValidateError()
    data class NoLastName(val hasError: Boolean) : ValidateError()
    data class NoEmail(val hasError: Boolean) : ValidateError()
    data class NoPassword(val hasError: Boolean) : ValidateError()
}
