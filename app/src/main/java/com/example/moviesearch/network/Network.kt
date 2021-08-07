package com.example.moviesearch.network

import android.util.Log
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

object Network {
    val client = OkHttpClient()
    //page 1 - 100
    fun getMovieCall(text:String,page: String): Call {
        //http://www.omdbapi.com/?apikey=[yourkey]&
        //2798e50c
        val host ="www.omdbapi.com"
        val APY_KEY: String ="2798e50c"
        val url : HttpUrl =HttpUrl.Builder()
            .scheme("http")
            .host(host)
            .addQueryParameter("apikey",APY_KEY)
            .addQueryParameter("s",text)
            .addQueryParameter("page",page)
            .build()
        Log.d("Server", "url = $url")
        var request:Request = Request.Builder()
            .get()
            .url(url)
            .build()
        return client.newCall(request)
    }
}