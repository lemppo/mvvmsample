package ru.touchin.kotlinsamples.data.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Author: Oksana Pokrovskaya
 * Email: lempo.developer@gmail.com
 */
@Entity()
data class Movie(
        val id: Int,
        val vote_average: Float,
        val title: String,
        val overview: String,
        val release_date: Date,
        val poster_path: String,
        @PrimaryKey(autoGenerate = true)
        val response_order: Int
)