package com.annaginagili.movieapp.ui.popular_fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.annaginagili.movieapp.model.MovieModel
import com.annaginagili.movieapp.model.SearchModel
import com.annaginagili.movieapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularFragmentViewModel: ViewModel() {
    private var movieLiveData = MutableLiveData<List<MovieModel>>()
    private val movies = ArrayList<MovieModel>()
    private var id: SearchModel? = null

    fun getPopular() {
        RetrofitClient.getInstance().getApi().getPopular().enqueue(object: Callback<SearchModel> {
            override fun onResponse(call: Call<SearchModel>, response: Response<SearchModel>) {
                if (response.body()!= null){
                    id = response.body()!!

                    if (id != null) {
                        for (i in id!!.results) {
                            getMovieById(i.id.toString())
                        }
                    }
                }
                else{
                    return
                }
            }

            override fun onFailure(call: Call<SearchModel>, t: Throwable) {

            }
        })
    }

    private fun getMovieById(id: String) {
        RetrofitClient.getInstance().getApi().getMovieById(id).enqueue(
            object: Callback<MovieModel> {
                override fun onResponse(
                    call: Call<MovieModel>,
                    response: Response<MovieModel>
                ) {
                    movies.add(response.body()!!)
                    movieLiveData.value = movies
                }

                override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

    fun observeMovieLiveData() : LiveData<List<MovieModel>> {
        return movieLiveData
    }
}