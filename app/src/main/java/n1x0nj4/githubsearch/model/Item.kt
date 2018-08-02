package n1x0nj4.githubsearch.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Item(
        val id: Int,
        val name: String,
        val owner: Owner,
        val description: String?,
        val html_url: String,
        val created_at: String,
        val updated_at: String,
        val language: String?,
        val forks_count: Int,
        val watchers_count: Int,
        val open_issues_count: Int
) : Parcelable