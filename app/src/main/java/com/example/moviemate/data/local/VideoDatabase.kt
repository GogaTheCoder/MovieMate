package com.example.moviemate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviemate.model.Video

@Database(entities = [Video::class], version = 1, exportSchema = false)
abstract class VideoDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao
}