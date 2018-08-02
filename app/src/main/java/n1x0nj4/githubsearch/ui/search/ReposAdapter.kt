package n1x0nj4.githubsearch.ui.search

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import n1x0nj4.githubsearch.R
import n1x0nj4.githubsearch.model.Item
import n1x0nj4.githubsearch.model.Owner
import n1x0nj4.githubsearch.util.Image

class ReposAdapter(private val context: Context) : PagedListAdapter<Item, ReposAdapter.SingleItemRowHolder>(itemsDiff) {

    private lateinit var onRepoClickListener: OnRepoClickListener

    private lateinit var onThumbnailClickListener: OnThumbnailClickListener

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SingleItemRowHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_repo, null)
        return SingleItemRowHolder(v)
    }

    override fun onBindViewHolder(holder: SingleItemRowHolder, i: Int) {
        val repo = getItem(i)

        repo?.let {
            Image.set(context, repo.owner.avatar_url, holder.thumbnail)

            holder.name.text = repo.name
            holder.author.text = repo.owner.login
            holder.watchers.text = repo.watchers_count.toString()
            holder.forks.text = repo.forks_count.toString()
            holder.issues.text = repo.open_issues_count.toString()

            holder.row.setOnClickListener {
                onRepoClickListener.onRepoClicked(repo)
            }

            holder.thumbnail.setOnClickListener {
                onThumbnailClickListener.onThumbnailClicked(repo.owner)
            }
        }
    }

    inner class SingleItemRowHolder(view: View) : RecyclerView.ViewHolder(view) {
        var row: LinearLayout = view.findViewById<View>(R.id.row_parent) as LinearLayout
        var thumbnail: ImageView = view.findViewById<View>(R.id.thumbnail) as ImageView
        var name: TextView = view.findViewById<View>(R.id.name) as TextView
        var author: TextView = view.findViewById<View>(R.id.author) as TextView
        var watchers: TextView = view.findViewById<View>(R.id.watchers) as TextView
        var forks: TextView = view.findViewById<View>(R.id.forks) as TextView
        var issues: TextView = view.findViewById<View>(R.id.issues) as TextView
    }

    fun setOnRepoClickListener(onRepoClickListener: OnRepoClickListener) {
        this.onRepoClickListener = onRepoClickListener
    }

    interface OnRepoClickListener {
        fun onRepoClicked(repo: Item)
    }

    interface OnThumbnailClickListener {
        fun onThumbnailClicked(owner: Owner)
    }

    fun setOnThumbnailClickListener(onThumbnailClickListener: OnThumbnailClickListener) {
        this.onThumbnailClickListener = onThumbnailClickListener
    }

    companion object {
        val itemsDiff = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(old: Item, new: Item): Boolean {
                return old.id == new.id
            }

            override fun areContentsTheSame(old: Item, new: Item): Boolean {
                return old == new
            }
        }
    }
}