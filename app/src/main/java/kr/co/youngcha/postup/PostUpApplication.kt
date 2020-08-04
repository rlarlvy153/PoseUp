package kr.co.youngcha.postup

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class PostUpApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppComponents.init(this)

        initTimber()

        startKoin()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun startKoin(){
        startKoin{
            androidContext(this@PostUpApplication)
            modules(koinModulesList)
        }
    }
}