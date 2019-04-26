package com.example.movieapp.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.movieapp.models.Result

@Database(entities = [Result::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}