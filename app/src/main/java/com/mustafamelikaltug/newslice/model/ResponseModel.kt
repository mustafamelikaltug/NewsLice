package com.mustafamelikaltug.newslice.model

data class Response(
    val totalArticles : Int?,
    val articles : List<Article>?
)