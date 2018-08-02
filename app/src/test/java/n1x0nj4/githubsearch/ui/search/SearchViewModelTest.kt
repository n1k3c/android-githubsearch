package n1x0nj4.githubsearch.ui.search

import android.arch.paging.PagedList
import com.nhaarman.mockitokotlin2.*
import io.reactivex.observers.DisposableObserver
import junit.framework.Assert.assertEquals
import n1x0nj4.githubsearch.data.search.GetReposInteractor
import n1x0nj4.githubsearch.data.search.Sort
import n1x0nj4.githubsearch.model.Item
import n1x0nj4.githubsearch.state.ResourceState
import n1x0nj4.githubsearch.util.DataFactory
import n1x0nj4.githubsearch.util.RepoFactory
import org.junit.Test
import org.mockito.Captor
import org.mockito.Mockito.`when` as whenever

/*
We are using paging library which return PagedList. We can'r mock/create PageList for tests, so I created fake
DataSource for that.
 */
class SearchViewModelTest : BaseViewModelTest() {

    private lateinit var searchViewModel: SearchViewModel

    private var getRepos: GetReposInteractor = mock()

    @Captor
    private val captor = argumentCaptor<DisposableObserver<PagedList<Item>>>()

    override fun setUp() {

        searchViewModel = SearchViewModel(getRepos)
    }

    @Test
    fun getReposExecutesUseCase() {
        val query = DataFactory.randomString()
        val sort = Sort.DEFAULT

        searchViewModel.fetchRepos(query, sort)

        verify(getRepos, times(1)).execute(any(), eq(query), eq(sort))
    }

    @Test
    fun getReposReturnsSuccess() {
        val repos = RepoFactory.makeFakeRepoPagedList()
        val query = DataFactory.randomString()
        val sort = Sort.DEFAULT

        searchViewModel.fetchRepos(query, sort)

        verify(getRepos).execute(captor.capture(), eq(query), eq(sort))

        captor.firstValue.onNext(repos!!)

        assertEquals(ResourceState.SUCCESS, searchViewModel.getRepos().value?.status)
    }

    @Test
    fun getReposReturnsError() {
        val repos = RepoFactory.makeFakeRepoPagedList()
        val query = DataFactory.randomString()
        val sort = Sort.DEFAULT

        searchViewModel.fetchRepos(query, sort)

        verify(getRepos).execute(captor.capture(), eq(query), eq(sort))

        captor.firstValue.onError(RuntimeException())

        assertEquals(ResourceState.ERROR, searchViewModel.getRepos().value?.status)
    }

    @Test
    fun fetchReposReturnsData() {
        val repos = RepoFactory.makeFakeRepoPagedList()
        val query = DataFactory.randomString()
        val sort = Sort.DEFAULT

        searchViewModel.fetchRepos(query, sort)

        verify(getRepos).execute(captor.capture(), eq(query), eq(sort))

        captor.firstValue.onNext(repos!!)

        assertEquals(repos, searchViewModel.getRepos().value?.data)
    }

    @Test
    fun fetchReposReturnsMessageForError() {
        val errorMessage = DataFactory.randomString()

        val query = DataFactory.randomString()
        val sort = Sort.DEFAULT

        searchViewModel.fetchRepos(query, sort)

        verify(getRepos).execute(captor.capture(), eq(query), eq(sort))
        captor.firstValue.onError(RuntimeException(errorMessage))

        assertEquals(errorMessage, searchViewModel.getRepos().value?.message)
    }
}