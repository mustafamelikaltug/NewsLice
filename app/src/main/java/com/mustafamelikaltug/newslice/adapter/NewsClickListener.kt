package com.mustafamelikaltug.newslice.adapter

import android.view.View

interface NewsClickListener {
    fun onArticleClicked(v:View, uuid:Int)
}