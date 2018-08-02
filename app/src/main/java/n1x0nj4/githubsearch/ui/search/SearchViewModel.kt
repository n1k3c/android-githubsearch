package n1x0nj4.githubsearch.ui.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.github.ajalt.timberkt.e
import io.reactivex.observers.DisposableObserver
import n1x0nj4.githubsearch.data.search.GetReposInteractor
import n1x0nj4.githubsearch.data.search.Sort
import n1x0nj4.githubsearch.model.Item
import n1x0nj4.githubsearch.state.Resource
import n1x0nj4.githubsearch.state.ResourceState
import javax.inject.Inject


class SearchViewModel @Inject constructor(private val getRepos: GetReposInteractor) : ViewModel() {

    private val reposResult: MutableLiveData<Resource<PagedList<Item>>> = MutableLiveData()

    fun getRepos(): LiveData<Resource<PagedList<Item>>> {
        return reposResult
    }

    fun fetchRepos(query: String, sort: Sort = Sort.DEFAULT) {
        reposResult.postValue(Resource(ResourceState.LOADING, null, null))

        getRepos.execute(ReposSubscriber(), query, sort)
    }

    inner class ReposSubscriber : DisposableObserver<PagedList<Item>>() {
        override fun onComplete() {
        }

        override fun onNext(data: PagedList<Item>) {
            reposResult.postValue(Resource(ResourceState.SUCCESS, data, null))
        }

        override fun onError(error: Throwable) {
            e { error.localizedMessage }
            reposResult.postValue(Resource(ResourceState.ERROR, null, error.localizedMessage))
        }
    }

    override fun onCleared() {
        getRepos.dispose()
        super.onCleared()
    }
}