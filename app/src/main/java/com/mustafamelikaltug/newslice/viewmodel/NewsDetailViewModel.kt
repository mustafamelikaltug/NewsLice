package com.mustafamelikaltug.newslice.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mustafamelikaltug.newslice.model.Article
import com.mustafamelikaltug.newslice.service.NewsDatabase
import kotlinx.coroutines.launch

class NewsDetailViewModel(application: Application) : BaseViewModel(application) {
    // MutableLiveData to hold the Article data
    val articleLiveData = MutableLiveData<Article>()

    // Function to fetch data from Room database using a provided UUID
    fun getDataFromRoom(uuid: Int) {
        launch {
            // Access the DAO from the NewsDatabase
            val dao = NewsDatabase(getApplication()).countryDao()
            // Get the country information by UUID and update the LiveData
            val country = dao.getArticle(uuid)
            articleLiveData.value = country
        }
    }
}