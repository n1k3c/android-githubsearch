package n1x0nj4.githubsearch.di.module

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import n1x0nj4.githubsearch.ui.repo.RepoActivity
import n1x0nj4.githubsearch.ui.search.SearchActivity
import n1x0nj4.githubsearch.ui.user.UserActivity
import n1x0nj4.githubsearch.util.PostExecutionThread
import n1x0nj4.githubsearch.util.UiThread

@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesSearchActivity(): SearchActivity

    // We don't need injection in this activities below. This is fix coz we are extending DaggerAppCompatActivity() in
    // BaseActivity. One solution is to remove this, but I decided to second solution which is - let's be prepared
    // for scalability and let go :)

    @ContributesAndroidInjector
    abstract fun contributesRepoActivity(): RepoActivity

    @ContributesAndroidInjector
    abstract fun contributesUserActivity(): UserActivity
}