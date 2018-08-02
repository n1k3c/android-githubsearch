package n1x0nj4.githubsearch.data.search

import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import n1x0nj4.githubsearch.api.ApiService
import n1x0nj4.githubsearch.data.BaseInteractor
import n1x0nj4.githubsearch.model.Item
import n1x0nj4.githubsearch.util.PostExecutionThread
import javax.inject.Inject


const val PAGE_SIZE = 20

private const val INITIAL_LOAD_SIZE = 40

private const val PREFETCH_DISTANCE = 10

open class GetReposInteractor @Inject constructor(private val postExecutionThread: PostExecutionThread)
    : BaseInteractor() {

    @Inject
    lateinit var apiService: ApiService

    open fun execute(singleObserver: DisposableObserver<PagedList<Item>>, query: String, sort: Sort):
            Observable<PagedList<Item>> {

        val config = PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setInitialLoadSizeHint(INITIAL_LOAD_SIZE)
                .setPrefetchDistance(PREFETCH_DISTANCE)
                .setEnablePlaceholders(false)
                .build()

        val sourceFactory = RepoDataSourceFactory(apiService, query, sort)

        val single = RxPagedListBuilder(sourceFactory, config)
                .buildObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutionThread.scheduler)

        addDisposable(single.subscribeWith(singleObserver))

        return single
    }
}