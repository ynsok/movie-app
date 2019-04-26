package com.example.movieapp.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.movieapp.models.Result

@Dao
interface MovieDao {
    @Query("SELECT * FROM Result")
    fun all(): LiveData<MutableList<Result>>

    @Insert
    fun insert(movie: Result)

    @Delete
    fun delete(movie: Result)
}