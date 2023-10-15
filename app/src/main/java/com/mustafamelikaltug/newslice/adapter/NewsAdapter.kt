package com.mustafamelikaltug.newslice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mustafamelikaltug.newslice.databinding.ArticleCardBinding
import com.mustafamelikaltug.newslice.model.Article
import com.mustafamelikaltug.newslice.util.downloadFromUrl
import com.mustafamelikaltug.newslice.util.placeholderProgressBar
import com.mustafamelikaltug.newslice.view.FeedFragmentDirections

class NewsAdapter(val articleList:ArrayList<Article>) : RecyclerView.Adapter<NewsAdapter.CountryViewHolder>(),NewsClickListener {
    class CountryViewHolder(val binding: ArticleCardBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryViewHolder {
        var binding = ArticleCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        // Bind the data to the ViewHolder's layout using data binding
        holder.binding.article = articleList[position]
        holder.binding.listener = this

        // Load the article image from a URL with a placeholder progress bar
        holder.binding.imageViewArticleImage.downloadFromUrl(
            articleList[position].articleImage,
            placeholderProgressBar(holder.itemView.context)
        )
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    //Fetching articles by swiping
    fun updateCountryList(newCountyList : List<Article>){
        articleList.clear()
        articleList.addAll(newCountyList)
        notifyDataSetChanged()
    }

    override fun onArticleClicked(v: View, uuid:Int) {
        val action = FeedFragmentDirections.actionFeedFragmentToDetailsFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }


}