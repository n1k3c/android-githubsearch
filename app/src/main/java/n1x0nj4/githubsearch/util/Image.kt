package n1x0nj4.githubsearch.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class Image {
    companion object {
        fun set(context: Context, url: String, imageView: ImageView) {
            Glide.with(context).load(url).into(imageView)
        }
    }
}