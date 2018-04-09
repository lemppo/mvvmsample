package ru.touchin.kotlinsamples.data.database

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import io.reactivex.Maybe

@Dao
interface MovieDao {

    @Insert(onConflict = REPLACE)
    fun insertAll(movies: List<Movie>)

    @Delete
    fun delete(person: Movie)

    @Query("SELECT * FROM movie")
    fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM movie ORDER BY response_order ASC")
    fun getMovies() : DataSource.Factory<Int, Movie>

    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getMovie(movieId: Int): Maybe<Movie>
}