package com.mustafamelikaltug.newslice.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
@Entity
@TypeConverters(SourceListConverter::class)
data class Article(
    @ColumnInfo("articleTitle")
    @SerializedName("title")
    val articleTitle : String?,

    @ColumnInfo("articleDescription")
    @SerializedName("description")
    val articleDescripton : String?,

    @ColumnInfo("articleContent")
    @SerializedName("content")
    val articleContent : String?,

    @ColumnInfo("articleUrl")
    @SerializedName("url")
    val articleUrl : String?,

    @ColumnInfo("articleImage")
    @SerializedName("image")
    val articleImage : String?,

    @ColumnInfo("articlePublishedTime")
    @SerializedName("publishedAt")
    val articlePublishedTime : String?,

    @ColumnInfo("articleSource")
    @SerializedName("source")
    val articleSource : Source?){

    @PrimaryKey(autoGenerate = true)
    var uuid :  Int = 0
}