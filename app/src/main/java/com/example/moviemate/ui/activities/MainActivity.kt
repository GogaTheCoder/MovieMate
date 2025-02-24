package com.example.moviemate.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviemate.databinding.ActivityMainBinding
import com.example.moviemate.ui.adapters.VideoAdapter
import com.example.moviemate.ui.viewmodels.VideoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: VideoViewModel by viewModels()
    private lateinit var videoAdapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshVideos()
        }
    }

    private fun observeViewModel() {
        viewModel.videos.observe(this, Observer { videos ->
            videoAdapter.submitList(videos)
            binding.swipeRefreshLayout.isRefreshing = false
        })

        viewModel.error.observe(this, Observer { error ->
            Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupRecyclerView() {
        videoAdapter = VideoAdapter { video ->
            val intent = Intent(this, VideoActivity::class.java).apply {
                putExtra("VIDEO_URL", video.videoUrl)
            }
            startActivity(intent)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = videoAdapter
        }
    }
}