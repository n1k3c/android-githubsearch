package n1x0nj4.githubsearch.ui.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_search.*
import n1x0nj4.githubsearch.R
import n1x0nj4.githubsearch.data.search.Sort
import n1x0nj4.githubsearch.di.ViewModelFactory
import n1x0nj4.githubsearch.model.Item
import n1x0nj4.githubsearch.model.Owner
import n1x0nj4.githubsearch.state.Resource
import n1x0nj4.githubsearch.state.ResourceState
import n1x0nj4.githubsearch.ui.common.BaseActivity
import n1x0nj4.githubsearch.ui.repo.RepoActivity
import n1x0nj4.githubsearch.ui.user.UserActivity
import javax.inject.Inject


private const val NO_DECORATION: Int = 0

private const val KEY_RECYCLER_STATE = "recycler_state"

private const val KEY_SEARCH_QUERY_STATE = "search_query_state"

const val KEY_REPO_INTENT = "repo_intent"

const val KEY_OWNER_INTENT = "user_intent"

class SearchActivity : BaseActivity(), ReposAdapter.OnRepoClickListener, SearchView.OnQueryTextListener,
        ReposAdapter.OnThumbnailClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SearchViewModel

    private lateinit var filterMenuItem: MenuItem

    private val adapter: ReposAdapter by lazy {
        ReposAdapter(this)
    }

    private var searchQuery: String = ""

    private var recyclerState: Parcelable? = null

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
    }

    override val contentViewResource: Int = R.layout.activity_search

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        filterMenuItem = menu.findItem(R.id.filter)
        filterMenuItem.isVisible = searchQuery.isNotEmpty()

        searchView.clearFocus()

        this.searchView = searchView
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.stars -> viewModel.fetchRepos(searchQuery, Sort.STARS)
            R.id.forks -> viewModel.fetchRepos(searchQuery, Sort.FORKS)
            R.id.updated -> viewModel.fetchRepos(searchQuery, Sort.UPDATED)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        // Show filter menu item only when query is populated
        query?.let {
            if (query == searchQuery) return false
            searchQuery = query
            filterMenuItem.isVisible = query.isNotEmpty()

            viewModel.fetchRepos(query)
        }
        return false
    }

    override fun onStart() {
        super.onStart()

        viewModel.getRepos().observe(this,
                Observer<Resource<PagedList<Item>>> {
                    it?.let {
                        handleDataState(it)
                    }
                })
    }

    private fun handleDataState(resource: Resource<PagedList<Item>>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                progress.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                noResults.visibility = View.GONE
                setupScreenForSuccess(resource.data)
            }
            ResourceState.ERROR -> {
                progress.visibility = View.GONE
                recyclerView.visibility = View.GONE
                noResults.text = getString(R.string.error_403)
                noResults.visibility = View.VISIBLE
            }
            ResourceState.LOADING -> {
                progress.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                noResults.visibility = View.GONE
            }
        }
    }

    private fun setupScreenForSuccess(repos: PagedList<Item>?) {
        progress.visibility = View.GONE
        repos?.let {
            if (repos.isNotEmpty()) {
                recyclerView.hasFixedSize()
                adapter.submitList(repos)
                adapter.setOnRepoClickListener(this)
                adapter.setOnThumbnailClickListener(this)
                recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                // Check if decoration exists coz overlapping
                if (recyclerView.itemDecorationCount == NO_DECORATION)
                    recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
                recyclerView.adapter = adapter
            } else {
                progress.visibility = View.GONE
                recyclerView.visibility = View.GONE
                noResults.text = getString(R.string.no_results)
                noResults.visibility = View.VISIBLE
            }

        } ?: run {
            progress.visibility = View.GONE
            recyclerView.visibility = View.GONE
            noResults.visibility = View.VISIBLE
        }
    }

    override fun onRepoClicked(repo: Item) {
        val i = Intent(this, RepoActivity::class.java)
        i.putExtra(KEY_REPO_INTENT, repo)
        startActivity(i)
    }

    override fun onThumbnailClicked(owner: Owner) {
        val i = Intent(this, UserActivity::class.java)
        i.putExtra(KEY_OWNER_INTENT, owner)
        startActivity(i)
    }

    override fun onResume() {
        super.onResume()

        if (recyclerState != null) {
            recyclerView.layoutManager.onRestoreInstanceState(recyclerState)
        }
        // SearchView is always in focus when onResume method is triggered. This is fix for that.
        if (::searchView.isInitialized) searchView.clearFocus()
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_SEARCH_QUERY_STATE, searchQuery)
        if (recyclerView.layoutManager != null) {
            recyclerState = recyclerView.layoutManager.onSaveInstanceState()
            outState.putParcelable(KEY_RECYCLER_STATE, recyclerState)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            searchQuery = savedInstanceState.getString(KEY_SEARCH_QUERY_STATE)
            if (recyclerView.layoutManager != null) {
                recyclerState = savedInstanceState.getParcelable(KEY_RECYCLER_STATE)
                recyclerView.layoutManager.onRestoreInstanceState(recyclerState)
            }
        }
        super.onRestoreInstanceState(savedInstanceState)
    }
}
