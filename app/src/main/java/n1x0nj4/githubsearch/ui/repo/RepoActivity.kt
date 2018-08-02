package n1x0nj4.githubsearch.ui.repo

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_repo.*
import n1x0nj4.githubsearch.R
import n1x0nj4.githubsearch.model.Item
import n1x0nj4.githubsearch.ui.common.BaseActivity
import n1x0nj4.githubsearch.ui.search.KEY_OWNER_INTENT
import n1x0nj4.githubsearch.ui.search.KEY_REPO_INTENT
import n1x0nj4.githubsearch.ui.user.UserActivity

class RepoActivity : BaseActivity(), View.OnClickListener {

    private lateinit var selectedRepo: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        val bundle = intent.extras
        if (bundle != null) {
            selectedRepo = bundle.getParcelable(KEY_REPO_INTENT)
            setRepoDetails(selectedRepo)
        }
    }

    override val contentViewResource: Int = R.layout.activity_repo

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) finish()

        return super.onOptionsItemSelected(item)
    }

    private fun setRepoDetails(repo: Item) {
        setActionBarTitle(repo.name)

        val desc = repo.description
        desc?.let {
            tvDescription.text = desc
        } ?: run {
            tvDescription.text = getString(R.string.text_not_available)
        }

        tvCreatedAt.text = repo.created_at
        tvUpdatedAt.text = repo.updated_at

        val language = repo.language
        language?.let {
            tvLanguage.text = language
        } ?: run {
            tvLanguage.text = getString(R.string.text_not_available)
        }

        bMoreInfo.setOnClickListener(this)
        bOwnerInfo.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bMoreInfo -> openLinkInBrowser(selectedRepo.html_url)
            R.id.bOwnerInfo -> {
                val i = Intent(this, UserActivity::class.java)
                i.putExtra(KEY_OWNER_INTENT, selectedRepo.owner)
                startActivity(i)
            }
        }
    }
}