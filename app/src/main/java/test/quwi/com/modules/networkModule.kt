package test.quwi.com.base

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import test.quwi.com.base.Constants.AUTH_MARKER
import test.quwi.com.base.Constants.NO_AUTH_MARKER
import java.util.concurrent.TimeUnit

const val BASE_API_URL = "https://api.quwi.com/v2/"

private const val READ_TIMEOUT = 30L
private const val WRITE_TIMEOUT = 30L
private const val CONNECT_TIMEOUT = 30L

val networkModule = module {
    single(named(NO_AUTH_MARKER)) { provideOkHttpClient(get()) }
    single(named(AUTH_MARKER)){ provideAuthOkHttpClient(get(), get()) }
    single(named(NO_AUTH_MARKER)) { provideRetrofit(get(named(NO_AUTH_MARKER))) }
    single(named(AUTH_MARKER)) { provideRetrofit(get(named(AUTH_MARKER))) }
    single { HttpLoggingInterceptor().apply { level = BODY } }
}

private fun provideOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor
): OkHttpClient =
    OkHttpClient.Builder().apply {
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        addInterceptor(loggingInterceptor)
    }
        .build()

private fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .client(client)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            )
        )
        .build()
}

private fun provideAuthOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    preferences: ISharedPreferences
): OkHttpClient =
    OkHttpClient.Builder().apply {
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        addInterceptor(loggingInterceptor)
        addInterceptor(Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${preferences.accessToken}")
                .build()
            chain.proceed(newRequest)
        })
    }
        .build()