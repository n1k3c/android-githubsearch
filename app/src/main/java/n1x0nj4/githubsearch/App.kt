package n1x0nj4.githubsearch

import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import n1x0nj4.githubsearch.di.DaggerAppComponent
import timber.log.Timber

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val builder = DaggerAppComponent.builder().application(this).build()
        builder.inject(this)
        return builder
    }
}