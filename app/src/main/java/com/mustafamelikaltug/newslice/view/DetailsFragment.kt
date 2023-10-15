package com.mustafamelikaltug.newslice.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mustafamelikaltug.newslice.databinding.FragmentDetailsBinding
import com.mustafamelikaltug.newslice.util.downloadFromUrl
import com.mustafamelikaltug.newslice.util.placeholderProgressBar
import com.mustafamelikaltug.newslice.viewmodel.NewsDetailViewModel

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var countryUUID = 0

    private lateinit var viewModel: NewsDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUUID = DetailsFragmentArgs.fromBundle(it).uuid
        }

        viewModel = ViewModelProvider(this)[NewsDetailViewModel::class.java]

        // Fetch data from Room database using the provided UUID
        viewModel.getDataFromRoom(countryUUID)

        observeViewModelFunc()

        // Set a click listener on the back button
        binding.imageViewBack.setOnClickListener {
            backButton(it)
        }
    }

    private fun observeViewModelFunc() {
        // Observe changes in articleLiveData
        viewModel.articleLiveData.observe(viewLifecycleOwner) { article ->
            article?.let {
                // Bind the article data to the layout using data binding
                binding.article = article
                context?.let {
                    // Load the article image from a URL with a placeholder progress bar
                    binding.imageViewArticleImage.downloadFromUrl(article.articleImage, placeholderProgressBar(it))
                }
            }
        }
    }

    private fun backButton(view: View) {
        // Navigate back to the FeedFragment
        val action = DetailsFragmentDirections.actionDetailsFragmentToFeedFragment()
        Navigation.findNavController(view).navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}