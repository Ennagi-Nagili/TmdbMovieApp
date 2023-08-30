package com.annaginagili.movieapp.ui.popular_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.annaginagili.movieapp.R
import com.annaginagili.movieapp.adapter.SearchAdapter
import com.annaginagili.movieapp.databinding.FragmentPopularBinding
import com.annaginagili.movieapp.ui.home_fragment.HomeFragmentDirections
import com.annaginagili.movieapp.ui.home_fragment.HomeFragmentViewModel

class PopularFragment : Fragment() {
    lateinit var binding: FragmentPopularBinding
    lateinit var populars: RecyclerView
    private lateinit var viewModel: PopularFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentPopularBinding.inflate(inflater)
        populars = binding.populars
        viewModel = ViewModelProvider(this)[PopularFragmentViewModel::class.java]

        viewModel.observeMovieLiveData().observe(requireActivity()) {
            populars.setHasFixedSize(true)
            populars.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val adapter = SearchAdapter(requireContext(), it)
            adapter.setOnItemClickListener(object: SearchAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    findNavController().navigate(
                        PopularFragmentDirections.actionPopularFragmentToDetailsFragment(
                        it[position]
                    ))
                }
            })
            populars.adapter = adapter
        }
        viewModel.getPopular()

        return binding.root
    }
}