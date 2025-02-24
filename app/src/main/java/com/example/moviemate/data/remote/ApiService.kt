package com.example.moviemate.data.remote

import com.example.moviemate.model.Video
import retrofit2.http.GET

interface ApiService {
    @GET("videos")
    suspend fun getVideos(): List<Video>
}