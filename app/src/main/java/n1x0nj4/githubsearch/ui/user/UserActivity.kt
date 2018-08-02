package n1x0nj4.githubsearch.ui.user

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_user.*
import n1x0nj4.githubsearch.R
import n1x0nj4.githubsearch.model.Owner
import n1x0nj4.githubsearch.ui.common.BaseActivity
import n1x0nj4.githubsearch.ui.search.KEY_OWNER_INTENT
import n1x0nj4.githubsearch.util.Image

class UserActivity : BaseActivity(), View.OnClickListener {

    private lateinit var selectedOwner: Owner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        val bundle = intent.extras
        if (bundle != null) {
            selectedOwner = bundle.getParcelable(KEY_OWNER_INTENT)
            setOwnerDetails(selectedOwner)
        }
    }

    override val contentViewResource: Int = R.layout.activity_user

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) finish()

        return super.onOptionsItemSelected(item)
    }

    private fun setOwnerDetails(owner: Owner) {
        setActionBarTitle(owner.login)
        Image.set(this, owner.avatar_url, ivThumbnail)
        tvType.text = owner.type

        bMoreInfo.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        openLinkInBrowser(selectedOwner.html_url)
    }
}