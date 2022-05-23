package test.quwi.com.auth.request

data class SignupRequestBody(
    val name: String,
    val email: String,
    val password: String
)
