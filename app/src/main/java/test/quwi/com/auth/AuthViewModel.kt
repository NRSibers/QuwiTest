package test.quwi.com.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
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

    private val mLoginLiveData = MutableLiveData<LoginResponse>()
    val loginLiveData: LiveData<LoginResponse>
        get() = mLoginLiveData

    fun signUp(userInfo: UserInfo) {
        if (!validateUserInfo(userInfo)) return

        val job = GlobalScope.launch {

            val result = authRepository.signup(
                "${userInfo.firstName} ${userInfo.lastName}",
                userInfo.email,
                userInfo.password
            )

            when(result) {
                is RequestResult.Success -> {
                    handleAuthResult(result.data)
                }

                is RequestResult.Error -> {
                    when(result.error) {
                        is HttpException -> {
                            val code = (result.error as HttpException).code()
                            if (code == 417) mValidateUserInfoLiveData.postValue(ValidateError.EmailAlreadyInUse)
                        }
                        else -> {

                        }
                    }

                }
            }
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
                    handleAuthResult(result.data)
                }

                is RequestResult.Error -> {

                }
            }
        }
    }

    private fun handleAuthResult(response: LoginResponse) {
        sharedPreferences.accessToken = response.token
        sharedPreferences.userId = response.appInit.user.id
        mLoginLiveData.postValue(response)
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