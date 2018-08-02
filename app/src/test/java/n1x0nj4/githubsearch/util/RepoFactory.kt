package n1x0nj4.githubsearch.util

import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import n1x0nj4.githubsearch.data.search.PAGE_SIZE
import n1x0nj4.githubsearch.model.Item
import n1x0nj4.githubsearch.model.Owner
import n1x0nj4.githubsearch.ui.search.fakedata.FakeDataRepoSourceFactory

object RepoFactory {

    private fun makeOwner(): Owner {
        return Owner(DataFactory.randomInt(), DataFactory.randomString(), DataFactory.randomString(),
                DataFactory.randomString(), DataFactory.randomString())
    }

    private fun makeRepo(): Item {
        return Item(DataFactory.randomInt(), DataFactory.randomString(), makeOwner(), DataFactory.randomString(),
                DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
                DataFactory.randomString(), DataFactory.randomInt(), DataFactory.randomInt(), DataFactory.randomInt())
    }

    fun makeRepoList(count: Int): MutableList<Item> {
        val repos = mutableListOf<Item>()
        repeat(count) {
            repos.add(makeRepo())
        }
        return repos
    }

    fun makeFakeRepoPagedList(): PagedList<Item>? {
        var fakeRepos: PagedList<Item>? = null

        val fakeDataSource = LivePagedListBuilder(FakeDataRepoSourceFactory(), PAGE_SIZE)
                .build()

        fakeDataSource.observeForever { it ->
            fakeRepos = it
        }
        return fakeRepos
    }


}