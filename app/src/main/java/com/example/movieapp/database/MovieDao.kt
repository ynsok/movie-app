package com.example.movieapp.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.support.design.circularreveal.CircularRevealHelper
import com.example.movieapp.models.Result

@Dao
interface MovieDao {
    @Query("SELECT * FROM Result")
    fun all(): LiveData<MutableList<Result>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Result)

    @Delete
    fun delete(movie: Result)
}