package ru.touchin.mvvmsample.presentation

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadUri(path: String?) {
    if (!path.isNullOrBlank())
        Glide.with(this.context)
                .load(path)
//                .centerCrop()
                .into(this)
}