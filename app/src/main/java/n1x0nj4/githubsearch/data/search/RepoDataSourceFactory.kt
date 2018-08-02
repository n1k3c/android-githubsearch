package n1x0nj4.githubsearch.data.search

import android.arch.paging.DataSource
import n1x0nj4.githubsearch.api.ApiService
import n1x0nj4.githubsearch.model.Item


class RepoDataSourceFactory(private val apiService: ApiService,
                            private val query: String,
                            private val sort: Sort
) : DataSource.Factory<Int, Item>() {

    override fun create(): DataSource<Int, Item> {
        return RepoDataSource(apiService, query, sort)
    }
}