package com.mehmetalioyur.findmovieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.mehmetalioyur.findmovieapp.databinding.FragmentDetailsBinding
import com.mehmetalioyur.findmovieapp.util.Constants
import com.mehmetalioyur.findmovieapp.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment() : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var glide: RequestManager

    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subsribeToObservers()


        binding.fab.setOnClickListener {
            viewModel.insertMovie()
            Toast.makeText(requireContext(), "Movie Saved", Toast.LENGTH_SHORT).show()
        }


    }

    private fun subsribeToObservers() {
        viewModel.movieDetails.observe(viewLifecycleOwner, {

            glide.load("${Constants.POSTER_BASE_URL}${it.poster_path}").into(binding.imageDetail)
            binding.movieName.text = it.original_title
            binding.releaseDateText.text = it.release_date
            binding.movieDescriptionText.text = it.overview
            binding.originalLanguageText.text = it.original_language
            binding.averageVoteText.text = it.vote_average.toString()

        })



    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}