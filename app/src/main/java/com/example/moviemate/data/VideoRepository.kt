package com.example.moviemate.data

import android.content.Context
import com.example.moviemate.data.local.VideoDao
import com.example.moviemate.data.remote.ApiService
import com.example.moviemate.model.Video
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import java.io.InputStreamReader
import javax.inject.Inject

class VideoRepository @Inject constructor(
    private val apiService: ApiService,
    private val videoDao: VideoDao,
    private val context: Context
) {
    suspend fun getVideos(): List<Video> {
        return try {
            apiService.getVideos()
        } catch (e: Exception) {
            loadVideosFromJson()
        }
    }

    suspend fun cacheVideos(videos: List<Video>) {
        videoDao.insertAll(videos)
    }

    fun getCachedVideos(): Flow<List<Video>> {
        return videoDao.getAllVideos()
    }

    private fun loadVideosFromJson(): List<Video> {
        val inputStream = context.assets.open("videos.json")
        val reader = InputStreamReader(inputStream)
        val videoList = Gson().fromJson<List<Video>>(reader, object : TypeToken<List<Video>>() {}.type)
        reader.close()
        inputStream.close()
        return videoList
    }
}
