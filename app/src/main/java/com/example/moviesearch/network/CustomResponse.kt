package com.example.moviesearch.network

import android.util.Log
import com.example.moviesearch.data.CustomSearch
import com.example.moviesearch.data.DataList
import com.example.moviesearch.data.DataListSearch
import com.google.gson.Gson
import org.json.JSONException
import java.io.IOException

class CustomResponse {
    //page 0 - 100
    fun responseMovie(text: String, page: String): List<DataList> {
        val pageInt = page.toInt()
        if (pageInt > 100 || pageInt <= 0) {
            Log.e("Server", "execute request error page>100 || page<=0")
            return emptyList()
        } else {
            try {
                val response = Network.getMovieCall(text, page).execute()
                val responsString = response.body?.string().orEmpty()
                Log.d("Server", "response body = $responsString")
                Log.d("Server", "response successful = ${response.isSuccessful}")
                val movieList: List<DataList> = parseresponseMovie(responsString)
                return movieList
            } catch (e: IOException) {
                Log.e("Server", "execute request error = ${e.message}", e)
                return emptyList()
            }
        }
    }

    private fun parseresponseMovie(bodyString: String): List<DataList> {
        try {
            val gson = Gson()
            val dataListSearch: DataListSearch =
                gson.fromJson(bodyString, DataListSearch::class.java)
            val movieList: List<DataList>? = dataListSearch.Search
            Log.d("ServerParse", "parse response successful = ${movieList}")
            return if (movieList == null) {
                emptyList()
            } else {
                movieList
            }
        } catch (e: JSONException) {
            Log.d("ServerParse", "parse response erorr = ${e.message}")
            return emptyList()
        }
    }
}