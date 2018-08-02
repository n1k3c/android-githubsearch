package n1x0nj4.githubsearch.data.search

import android.arch.paging.PageKeyedDataSource
import com.github.ajalt.timberkt.e
import n1x0nj4.githubsearch.api.ApiService
import n1x0nj4.githubsearch.model.Item
import n1x0nj4.githubsearch.model.ReposResponse

class RepoDataSource(private val apiService: ApiService,
                     private val query: String,
                     private val sort: Sort
) : PageKeyedDataSource<Int, Item>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Item>) {
        apiService.getRepos(query, sort = sort.value, page = 1)
                .map { t: ReposResponse ->
                    t.items
                }
                .subscribe(
                        { t ->
                            callback.onResult(t, 1, 2)
                        },
                        { it ->
                            e { it.localizedMessage }
                            // callback.onResult(emptyList(), null, null)
                        }
                )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        val page = params.key
        val nextPage = page + 1

        apiService.getRepos(query, sort = sort.value, page = nextPage)
                .map { t: ReposResponse ->
                    t.items
                }
                .subscribe(
                        { t ->
                            callback.onResult(t, nextPage + 1)
                        },
                        { it ->
                            e { it.localizedMessage }
                            // callback.onResult(emptyList(), null)
                        }
                )

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
    }
}