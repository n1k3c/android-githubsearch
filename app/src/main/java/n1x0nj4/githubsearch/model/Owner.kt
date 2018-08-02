package n1x0nj4.githubsearch.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Owner(
        val id: Int,
        val login: String,
        val avatar_url: String,
        val html_url: String,
        val type: String
) : Parcelable