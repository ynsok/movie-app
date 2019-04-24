package com.example.movieapp.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.movieapp.models.Result

@Dao
interface MovieDao {
    @Query("SELECT * FROM Result")
    fun all(): LiveData<MutableList<Result>>

    @Insert
    fun insert(movie: Result): Long

    @Delete
    fun delete(movie: Result)
}