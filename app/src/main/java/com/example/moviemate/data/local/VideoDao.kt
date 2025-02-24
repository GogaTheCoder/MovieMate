package com.example.moviemate.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviemate.model.Video
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(videos: List<Video>)

    @Query("SELECT * FROM videos")
    fun getAllVideos(): Flow<List<Video>>

    @Query("SELECT * FROM videos WHERE id = :videoId")
    suspend fun getVideoById(videoId: String): Video?

    @Delete
    suspend fun deleteVideo(video: Video)

    @Query("DELETE FROM videos")
    suspend fun deleteAllVideos()
}