package com.example.moviemate.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviemate.ui.adapters.VideoAdapter
import com.example.moviemate.ui.viewmodels.VideoViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.moviemate.databinding.FragmentVideoListBinding
import com.example.moviemate.ui.activities.VideoActivity

@AndroidEntryPoint
class VideoListFragment : Fragment() {

    private var _binding: FragmentVideoListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VideoViewModel by viewModels()
    private lateinit var videoAdapter: VideoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupRecyclerView()
        observeViewModel()

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshVideos()
        }
    }

    private fun setupRecyclerView() {
        videoAdapter = VideoAdapter { video ->
            val intent = Intent(requireContext(), VideoActivity::class.java).apply {
                putExtra("VIDEO_URL", video.videoUrl)
            }
            startActivity(intent)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = videoAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.videos.observe(viewLifecycleOwner) { videos ->
            videoAdapter.submitList(videos)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_SHORT).show()
        }

        viewModel.selectedVideo.observe(viewLifecycleOwner) { video ->
            video?.let {
                val intent = Intent(requireContext(), VideoActivity::class.java).apply {
                    putExtra("VIDEO_URL", video.videoUrl)
                }
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}