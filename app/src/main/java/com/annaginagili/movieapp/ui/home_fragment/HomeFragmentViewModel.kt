package com.annaginagili.movieapp.ui.home_fragment
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

class HomeFragmentViewModel: ViewModel() {
    private var movieLiveData = MutableLiveData<List<MovieModel>>()
    private var popularLiveData = MutableLiveData<List<MovieModel>>()
    private val movies = ArrayList<MovieModel>()
    private var id: SearchModel? = null
    private val populars = ArrayList<MovieModel>()
    private var popId: SearchModel? = null

    fun searchMovie(query: String) {
        movieLiveData.value = null

        RetrofitClient.getInstance().getApi().searchMovie(query)
            .enqueue(object: Callback<SearchModel> {
                override fun onResponse(call: Call<SearchModel>, response: Response<SearchModel>) {
                    if (response.body()!= null){
                        id = response.body()!!

                        if (id != null) {
                            for (i in id!!.results) {
                                getMovieById(i.id.toString(), 1)
                            }
                        }
                    }
                    else{
                        return
                    }
                }

                override fun onFailure(call: Call<SearchModel>, t: Throwable) {
                    Log.e("hello", t.message.toString())
                }
            })
    }

    private fun getMovieById(id: String, type: Int) {
        RetrofitClient.getInstance().getApi().getMovieById(id).enqueue(
            object: Callback<MovieModel> {
                override fun onResponse(
                    call: Call<MovieModel>,
                    response: Response<MovieModel>
                ) {
                    if (type == 1) {
                        movies.add(response.body()!!)
                        movieLiveData.value = movies
                    } else {
                        populars.add(response.body()!!)
                        popularLiveData.value = populars
                    }
                }

                override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

    fun getPopular() {
        RetrofitClient.getInstance().getApi().getPopular().enqueue(object: Callback<SearchModel> {
            override fun onResponse(call: Call<SearchModel>, response: Response<SearchModel>) {
                if (response.body()!= null){
                    popId = response.body()!!

                    if (popId != null) {
                        for (i in popId!!.results) {
                            getMovieById(i.id.toString(), 2)
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

    fun observeMovieLiveData() : LiveData<List<MovieModel>> {
        return movieLiveData
    }

    fun observePopularLiveData() : LiveData<List<MovieModel>> {
        return popularLiveData
    }
}