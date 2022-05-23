package test.quwi.com

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import test.quwi.com.base.networkModule
import test.quwi.com.modules.apiModule
import test.quwi.com.modules.appModule

class QuwiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        //Start Koint
        startKoin {
            androidContext(this@QuwiApp)
            modules(listOf(appModule, apiModule, networkModule))
        }

    }
}