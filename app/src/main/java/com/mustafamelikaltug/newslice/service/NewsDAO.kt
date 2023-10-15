package com.mustafamelikaltug.newslice.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mustafamelikaltug.newslice.model.Article

@Dao
interface NewsDAO {
@Insert
suspend fun insertAll (vararg article: Article) : List<Long>

@Query("SELECT * FROM Article")
suspend fun getAllArticles() : List<Article>

@Query("SELECT * FROM Article WHERE uuid = :articleId")
suspend fun getArticle(articleId : Int) : Article

}