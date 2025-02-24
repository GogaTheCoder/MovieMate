package com.example.moviemate.di

import android.app.Application
import androidx.room.Room
import dagger.Provides
import javax.inject.Singleton
import com.example.moviemate.data.local.VideoDatabase
import com.example.moviemate.utils.Constants.DATABASE_NAME

object NetworkModule {
    @Provides
    @Singleton
    fun provideVideoDatabase(app: Application): VideoDatabase {
        return Room.databaseBuilder(app, VideoDatabase::class.java, DATABASE_NAME)
            .build()
    }
}