package com.annaginagili.movieapp.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.annaginagili.movieapp.R
import com.annaginagili.movieapp.adapter.BoardAdapter
import com.annaginagili.movieapp.databinding.FragmentOnBoardingBinding
import com.annaginagili.movieapp.model.BoardItem

class OnBoardingFragment : Fragment() {
    lateinit var binding: FragmentOnBoardingBinding
    lateinit var pager: ViewPager2
    lateinit var preferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentOnBoardingBinding.inflate(inflater)
        pager = binding.pager
        preferences = requireActivity().getSharedPreferences("MovieApp", AppCompatActivity.MODE_PRIVATE)

        if (!preferences.getBoolean("firstTime", false)) {
            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToHomeFragment2())
        }

        val item1 = BoardItem(
            R.drawable.many_film,
            "The biggest international and local film streaming",
            "Semper in cursus " +
                    "magna et eu varius nunc adipiscing. Elementum justo, l" +
                    "aoreet id sem semper parturient. ",
            R.drawable.indicator1,
            R.drawable.board_button_1
        )

        val item2 = BoardItem(
            R.drawable.girl_phone,
            "Offers ad-free viewing of high quality",
            "Semper in cursus " +
                    "magna et eu varius nunc adipiscing. Elementum justo, l" +
                    "aoreet id sem semper parturient. ",
            R.drawable.indicator2,
            R.drawable.board_button_2
        )

        val item3 = BoardItem(
            R.drawable.popcorn,
            "Our service brings together your favorite series",
            "Semper in cursus " +
                    "magna et eu varius nunc adipiscing. Elementum justo, l" +
                    "aoreet id sem semper parturient. ",
            R.drawable.indicator3,
            R.drawable.board_button_3
        )

        val adapter = BoardAdapter(requireContext(), arrayListOf(item1, item2, item3))

        adapter.setOnItemClickListener(object : BoardAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
               if (position < 2) {
                   pager.currentItem = position + 1
               } else {
                   preferences.edit().putBoolean("firstTime", false).apply()
                   findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment())
               }
            }
        })

        pager.adapter = adapter
        return binding.root
    }
}