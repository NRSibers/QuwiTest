package test.quwi.com.modules

import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import test.quwi.com.auth.AuthApi
import test.quwi.com.base.Constants
import test.quwi.com.chat.repository.ChatApi
import test.quwi.com.users.UsersApi

val apiModule = module {

    single {
        get<Retrofit>(named(Constants.NO_AUTH_MARKER)).create(AuthApi::class.java)
    }

    single {
        get<Retrofit>(named(Constants.AUTH_MARKER)).create(ChatApi::class.java)
    }

    single {
        get<Retrofit>(named(Constants.AUTH_MARKER)).create(UsersApi::class.java)
    }
}