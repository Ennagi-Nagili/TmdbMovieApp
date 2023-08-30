package com.annaginagili.movieapp.ui.home_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.annaginagili.movieapp.R
import com.annaginagili.movieapp.adapter.CategoriesAdapter
import com.annaginagili.movieapp.adapter.PopularAdapter
import com.annaginagili.movieapp.adapter.SearchAdapter
import com.annaginagili.movieapp.adapter.TopAdapter
import com.annaginagili.movieapp.databinding.FragmentHomeBinding
import com.annaginagili.movieapp.model.CategoriesItem
import com.annaginagili.movieapp.model.MovieModel
import com.annaginagili.movieapp.model.TopItem
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var viewPager2: ViewPager2
    lateinit var categories: RecyclerView
    private lateinit var popular: RecyclerView
    lateinit var topList: ArrayList<TopItem>
    lateinit var categoryList: List<CategoriesItem>
    private lateinit var auth: FirebaseAuth
    lateinit var main: ConstraintLayout
    lateinit var search: EditText
    lateinit var cancel: TextView
    private lateinit var search_layout: ConstraintLayout
    lateinit var viewModel: HomeFragmentViewModel
    private lateinit var search_recycler: RecyclerView
    lateinit var see_all: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater)
        viewPager2 = binding.pager
        categories = binding.categories
        popular = binding.popular
        auth = FirebaseAuth.getInstance()
        main = binding.main
        search = binding.search
        cancel = binding.cancel
        search_layout = binding.searchLayout
        viewModel = ViewModelProvider(this)[HomeFragmentViewModel::class.java]
        search_recycler = binding.searchRecycler
        see_all = binding.seeAll

        if (auth.currentUser == null) {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
        }

        topList = arrayListOf(TopItem(R.drawable.loot, "Lootcase", "On March 2, 2022"),
            TopItem(R.drawable.wakanda, "Black Panther: Wakanda Forever", "On March 2, 2022"),
            TopItem(R.drawable.life, "Life of PI", "On March 2, 2022")
        )

        viewPager2.adapter = TopAdapter(requireContext(), topList)

        categoryList = listOf(CategoriesItem(R.drawable.categories_active, "All", "#12CDD9"),
            CategoriesItem(0, "Comedy", "#FFFFFFFF"),
            CategoriesItem(0, "Animation", "#FFFFFFFF"),
            CategoriesItem(0, "Documentation", "#FFFFFFFF")
        )
        categories.setHasFixedSize(true)
        categories.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val categoryAdapter = CategoriesAdapter(requireContext(), categoryList)
        categoryAdapter.setOnItemClickListener(object: CategoriesAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val list = ArrayList<CategoriesItem>()
                for (i in categoryList) {
                    if (i != categoryList[position]) {
                        list.add(CategoriesItem(0, i.text, "#FFFFFFFF"))
                    } else {
                        list.add(CategoriesItem(R.drawable.categories_active, i.text, "#12CDD9"))
                    }
                }
                categoryList = list
                categoryAdapter.notifyDataSetChanged()
            }
        })
        categories.adapter = categoryAdapter

        viewModel.observePopularLiveData().observe(requireActivity()) {
            popular.setHasFixedSize(true)
            popular.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            val popularAdapter = PopularAdapter(requireContext(), it)
            popularAdapter.setOnItemClickListener(object: PopularAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                        it[position]
                    ))
                }
            })
            popular.adapter = popularAdapter
        }

        viewModel.getPopular()

        see_all.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPopularFragment())
        }

        search.setOnClickListener {
            main.visibility = View.GONE
            cancel.visibility = View.VISIBLE
            (search.layoutParams as ViewGroup.MarginLayoutParams).setMargins(48, 104, 150, 0)
            search_layout.visibility = View.VISIBLE
        }

        cancel.setOnClickListener {
            cancel.visibility = View.INVISIBLE
            search_layout.visibility = View.INVISIBLE
            (search.layoutParams as ViewGroup.MarginLayoutParams).setMargins(48, 310, 48, 0)
            main.visibility = View.VISIBLE
        }

        viewModel.observeMovieLiveData().observe(requireActivity()) {
            search_recycler.setHasFixedSize(true)
            search_recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val adapter = SearchAdapter(requireContext(), it)
            adapter.setOnItemClickListener(object: SearchAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                        it[position]
                    ))
                }
            })
            search_recycler.adapter = adapter
        }

        search.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchMovie(search.text.toString())
            }

            return@setOnEditorActionListener true
        }

        return binding.root
    }
}