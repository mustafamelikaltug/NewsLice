package com.mustafamelikaltug.newslice.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mustafamelikaltug.newslice.R

// Function to download and display an image from a URL into an ImageView
fun ImageView.downloadFromUrl(url: String?, progressDrawable: CircularProgressDrawable) {
    // Set up Glide options with a placeholder and error image
    val options = RequestOptions().placeholder(progressDrawable).error(R.drawable.ic_launcher_foreground)

    // Use Glide to load and display the image
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

// Function to create and return a circular progress bar
fun placeholderProgressBar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}



