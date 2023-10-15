package com.mustafamelikaltug.newslice.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustafamelikaltug.newslice.adapter.NewsAdapter
import com.mustafamelikaltug.newslice.databinding.FragmentFeedBinding
import com.mustafamelikaltug.newslice.viewmodel.NewsFeedViewModel

class FeedFragment : Fragment() {

    private lateinit var viewModel: NewsFeedViewModel
    private val newsAdapter = NewsAdapter(arrayListOf())
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NewsFeedViewModel::class.java]
        viewModel.refreshData()
        binding.recyclerViewArticle.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewArticle.adapter = newsAdapter

        // Set up a refresh listener for the swipeRefreshLayout
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.recyclerViewArticle.visibility = View.GONE
            binding.textViewArticleError.visibility = View.GONE
            binding.progressBarArticlesLoading.visibility = View.VISIBLE
            viewModel.refreshFromAPI()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        // Observe changes in the articles data
        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            articles?.let {
                binding.recyclerViewArticle.visibility = View.VISIBLE
                newsAdapter.updateCountryList(it)
            }
        }

        // Observe changes in the article error flag
        viewModel.articleError.observe(viewLifecycleOwner) { isError ->
            isError?.let {
                if (isError) {
                    binding.textViewArticleError.visibility = View.VISIBLE
                } else {
                    binding.textViewArticleError.visibility = View.GONE
                }
            }
        }

        // Observe changes in the article loading flag
        viewModel.articleLoading.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                if (isLoading) {
                    binding.progressBarArticlesLoading.visibility = View.VISIBLE
                    binding.textViewArticleError.visibility = View.GONE
                    binding.recyclerViewArticle.visibility = View.GONE
                } else {
                    binding.progressBarArticlesLoading.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}