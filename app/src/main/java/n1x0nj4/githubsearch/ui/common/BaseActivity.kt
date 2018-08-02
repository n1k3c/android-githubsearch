package n1x0nj4.githubsearch.ui.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.inputmethod.InputMethodManager
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import n1x0nj4.githubsearch.R

abstract class BaseActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentViewResource)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        if (toolbar != null) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(true)
        }
    }

    protected abstract val contentViewResource: Int

    fun injectDependencies() {
        AndroidInjection.inject(this)
    }

    private fun hideKeyboard() {
        val focusedView = currentFocus
        if (focusedView != null) {
            focusedView.clearFocus()
            val imm = focusedView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(focusedView.windowToken, 0)
        }
    }


    fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).setDisplayShowTitleEnabled(true)
            supportActionBar?.title = title
        }
    }

    override fun onBackPressed() {
        val parentActivityIntent = NavUtils.getParentActivityIntent(this)
        // if a parent activity is defined via manifest this will return an intent
        if (parentActivityIntent != null) {
            parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(parentActivityIntent)
            finish()
        } else {
            super.onBackPressed()
        }
    }

    fun openLinkInBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}
