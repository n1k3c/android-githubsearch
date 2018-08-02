package n1x0nj4.githubsearch.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import n1x0nj4.githubsearch.App
import n1x0nj4.githubsearch.di.module.AppModule
import n1x0nj4.githubsearch.di.module.UiModule
import n1x0nj4.githubsearch.di.module.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, UiModule::class, ViewModelModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(app: App)

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}