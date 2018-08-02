package n1x0nj4.githubsearch.ui.search.fakedata

import android.arch.paging.DataSource
import n1x0nj4.githubsearch.model.Item

class FakeDataRepoSourceFactory : DataSource.Factory<Int, Item>() {

    override fun create(): DataSource<Int, Item> {
        return FakeRepoDataSource()
    }
}