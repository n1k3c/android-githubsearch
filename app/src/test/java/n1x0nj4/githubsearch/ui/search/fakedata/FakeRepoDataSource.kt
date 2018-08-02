package n1x0nj4.githubsearch.ui.search.fakedata

import android.arch.paging.PageKeyedDataSource
import n1x0nj4.githubsearch.model.Item
import n1x0nj4.githubsearch.util.RepoFactory

class FakeRepoDataSource : PageKeyedDataSource<Int, Item>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Item>) {
        val items = RepoFactory.makeRepoList(2)
        callback.onResult(items, 1, 2)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
    }
}