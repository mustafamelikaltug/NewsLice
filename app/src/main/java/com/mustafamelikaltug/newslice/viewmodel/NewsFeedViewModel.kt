package com.mustafamelikaltug.newslice.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mustafamelikaltug.newslice.model.Article
import com.mustafamelikaltug.newslice.model.Response
import com.mustafamelikaltug.newslice.service.NewsDatabase
import com.mustafamelikaltug.newslice.service.RetrofitAPIService
import com.mustafamelikaltug.newslice.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : BaseViewModel(application) {
    private val retrofitAPIService = RetrofitAPIService()
    private val disposable =  CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(application)
    private var refreshTime = 60*60*1000*1000*1000L // Converting 1 hour to nanosecond

    val articles = MutableLiveData<List<Article>>()
    val articleError = MutableLiveData<Boolean>()
    val articleLoading = MutableLiveData<Boolean>()

    // Function to refresh data from SQLite if within the refresh time, otherwise fetch from the API
    fun refreshData(){
        val updateTime = customPreferences.getTime()

        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLite()
        } else {
            getDataFromAPI()
        }
    }

    // Function to force a refresh from the API
    fun refreshFromAPI(){
        getDataFromAPI()
    }

    // Function to fetch data from SQLite
    private fun getDataFromSQLite(){
        articleLoading.value = true
        launch {
            val countries = NewsDatabase(getApplication()).countryDao().getAllArticles()
            showArticles(countries)
        }
    }

    // Function to fetch data from the API
    private fun getDataFromAPI(){
        articleLoading.value = true

        disposable.add(retrofitAPIService.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Response>(){
                override fun onSuccess(t: Response) {
                    if (!t.articles.isNullOrEmpty()){
                        storeInSQLite(t.articles)
                    }
                    else{
                        Log.e("Save Database", "onSuccess: article list is empty!")
                    }
                }

                override fun onError(e: Throwable) {
                    articleLoading.value = false
                    articleError.value = true
                    Log.e("Save Database",e.localizedMessage)
                }
            }))
    }

    // Function to update LiveData with fetched articles
    private fun showArticles(articleList : List<Article>){
        articles.value = articleList
        articleError.value = false
        articleLoading.value = false
    }

    // Function to store articles in SQLite database
    private fun storeInSQLite(articleList : List<Article>){
        launch {
            val dao = NewsDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listUUIDPrimaryKey =dao.insertAll(*articleList.toTypedArray())

            var i = 0
            while (i < articleList.size){
                articleList[i].uuid = listUUIDPrimaryKey[i].toInt()
                i++
            }

            showArticles(articleList)
        }

        customPreferences.saveTime(System.nanoTime())
    }

    // Clear disposables when ViewModel is no longer in use
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}