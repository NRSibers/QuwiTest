package test.quwi.com.modules

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import test.quwi.com.base.ISharedPreferences
import test.quwi.com.auth.AuthRepositoryImpl
import test.quwi.com.auth.AuthViewModel
import test.quwi.com.auth.IAuthRepository
import test.quwi.com.base.Constants
import test.quwi.com.base.SharedPreferencesImpl
import test.quwi.com.chat.repository.ChatRepositoryImpl
import test.quwi.com.chat.ChatViewModel
import test.quwi.com.chat.repository.IChatRepository
import test.quwi.com.main.MainViewModel

val appModule = module {
    single<IAuthRepository> {
        AuthRepositoryImpl(get())
    }

    single<IChatRepository> {
        ChatRepositoryImpl(get(), get())
    }

    viewModel {
        AuthViewModel(get(), get())
    }

    viewModel {
        ChatViewModel(get(), get())
    }

    viewModel {
        MainViewModel(get(), get())
    }

    single<SharedPreferences> {
        androidContext().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    single<ISharedPreferences> {
        SharedPreferencesImpl(get())
    }
}