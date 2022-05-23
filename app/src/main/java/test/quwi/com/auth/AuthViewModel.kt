package test.quwi.com.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import test.quwi.com.base.ISharedPreferences
import test.quwi.com.auth.response.LoginResponse
import test.quwi.com.base.RequestResult

class AuthViewModel (
    private val authRepository: IAuthRepository,
    private val sharedPreferences: ISharedPreferences
    ) : ViewModel() {

    private val mValidateUserInfoLiveData = MutableLiveData<ValidateError>()
    val validateUserInfoLiveData: LiveData<ValidateError>
        get() = mValidateUserInfoLiveData

    private val mSignupLiveData = MutableLiveData<RequestResult<Unit>>()
    val signupLiveData: LiveData<RequestResult<Unit>>
        get() = mSignupLiveData

    private val mLoginLiveData = MutableLiveData<RequestResult<LoginResponse>>()
    val loginLiveData: LiveData<RequestResult<LoginResponse>>
        get() = mLoginLiveData

    fun signUp(userInfo: UserInfo) {
        if (!validateUserInfo(userInfo)) return

        val job = GlobalScope.launch {

            val result = authRepository.signup(
                "${userInfo.firstName} ${userInfo.lastName}",
                userInfo.email,
                userInfo.password
            )

            mSignupLiveData.postValue(result)
        }
    }

    fun login(login: String, password: String) {
        if (!validateLoginAndPassword(login, password)) return

        val job = GlobalScope.launch {

            val result = authRepository.login(
                login,
                password
            )
            when(result) {
                is RequestResult.Success -> {
                    sharedPreferences.accessToken = result.data.token
                    sharedPreferences.userId = result.data.appInit.user.id
                }

                is RequestResult.Error -> {

                }
            }
            mLoginLiveData.postValue(result)
        }
    }

    private fun validateUserInfo(userInfo: UserInfo) : Boolean {

        mValidateUserInfoLiveData.value = ValidateError.NoFirstName(userInfo.firstName.isEmpty())

        mValidateUserInfoLiveData.value = ValidateError.NoLastName(userInfo.lastName.isEmpty())

        mValidateUserInfoLiveData.value = ValidateError.NoEmail(userInfo.email.isEmpty())

        mValidateUserInfoLiveData.value = ValidateError.NoPassword(userInfo.password.isEmpty())
        return userInfo.firstName.isNotEmpty() && userInfo.lastName.isNotEmpty() && userInfo.email.isNotEmpty() && userInfo.password.isNotEmpty()
    }

    private fun validateLoginAndPassword(login: String, password: String) : Boolean {
        mValidateUserInfoLiveData.value = ValidateError.NoEmail(login.isEmpty())
        mValidateUserInfoLiveData.value = ValidateError.NoPassword(password.isEmpty())

        return login.isNotEmpty() && password.isNotEmpty()
    }
}