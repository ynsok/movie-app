package com.example.movieapp.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Delete
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