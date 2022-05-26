package test.quwi.com.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import test.quwi.com.auth.IAuthRepository
import test.quwi.com.base.FragmentRoute
import test.quwi.com.base.ISharedPreferences
import test.quwi.com.base.RequestResult

class MainViewModel(
    private val sharedPreferences: ISharedPreferences,
    private val authRepository: IAuthRepository
) : ViewModel() {
    private val mRouteLiveData = MutableLiveData<FragmentRoute>()
    val routeLiveData: LiveData<FragmentRoute>
        get() = mRouteLiveData

    fun init() {
        if(sharedPreferences.accessToken.isNullOrEmpty()) {
            openFragment(FragmentRoute.AuthFragment)
        } else {
            openFragment(FragmentRoute.ChatFragment)
        }
    }

    fun openFragment(fragmentRoute: FragmentRoute) {
        mRouteLiveData.postValue(fragmentRoute)
    }

    fun logout() {
        GlobalScope.launch {
            when(authRepository.logout(false)) {
                is RequestResult.Success -> {
                    sharedPreferences.logout()
                    openFragment(FragmentRoute.AuthFragment)
                }

                is RequestResult.Error -> {

                }
            }
        }
    }
}