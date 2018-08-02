package n1x0nj4.githubsearch.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module(includes = [ApiModule::class])
abstract class AppModule {

    @Binds
    abstract fun provideContext(application: Application): Context
}