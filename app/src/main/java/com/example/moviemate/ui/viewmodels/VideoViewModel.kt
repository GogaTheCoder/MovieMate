package com.example.moviemate.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviemate.data.VideoRepository
import com.example.moviemate.model.Video
import com.example.moviemate.ui.adapters.VideoAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val repository: VideoRepository
) : ViewModel() {

    private val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>> = _videos

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    val videoAdapter = VideoAdapter { video ->
        _selectedVideo.value = video
    }

    private val _selectedVideo = MutableLiveData<Video>()
    val selectedVideo: LiveData<Video> = _selectedVideo

    init {
        loadVideos()
    }

    private fun loadVideos() {
        viewModelScope.launch {
            try {
                val videos = repository.getVideos()
                _videos.value = videos
                repository.cacheVideos(videos)
            } catch (e: Exception) {
                _error.value = e.message
                repository.getCachedVideos().collect { cachedVideos ->
                    _videos.value = cachedVideos
                }
            }
        }
    }

    fun refreshVideos() {
        viewModelScope.launch {
            try {
                val videos = repository.getVideos()
                _videos.value = videos
                repository.cacheVideos(videos)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
