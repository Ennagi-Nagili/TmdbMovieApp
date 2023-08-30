package com.annaginagili.movieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.annaginagili.movieapp.R
import com.annaginagili.movieapp.api.Constants
import com.annaginagili.movieapp.databinding.FragmentDetailsBinding
import com.bumptech.glide.Glide

class DetailsFragment : Fragment() {
    lateinit var binding: FragmentDetailsBinding
    lateinit var title: TextView
    lateinit var poster: ImageView
    lateinit var year: TextView
    lateinit var time: TextView
    lateinit var film: TextView
    lateinit var story: TextView
    private val args by navArgs<DetailsFragmentArgs>()
    lateinit var big_poster: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentDetailsBinding.inflate(inflater)
        title = binding.title
        poster = binding.poster
        year = binding.year
        time = binding.time
        film = binding.film
        story = binding.story
        big_poster = binding.bigPoster

        title.text = args.movie.title
        Glide.with(requireActivity()).load(Constants.imageUrl + args.movie.poster_path).into(poster)
        Glide.with(requireActivity()).load(Constants.imageUrl + args.movie.poster_path).into(big_poster)
        year.text = args.movie.release_date.subSequence(0, 4)
        time.text = args.movie.runtime.toString() + " Minutes"
        var genres = ""
        for (i in args.movie.genres) {
            genres += i.name + " "
        }
        film.text = genres
        story.text = args.movie.overview

        return binding.root
    }
}