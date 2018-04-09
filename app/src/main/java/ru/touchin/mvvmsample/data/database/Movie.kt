package ru.touchin.kotlinsamples.data.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Author: Oksana Pokrovskaya
 * Email: lempo.developer@gmail.com
 */
@Entity()
class Movie(
        var id: Int = 0,
        var vote_average: Float = 0f,
        var title: String = "",
        var overview: String = "",
        var release_date: Date = Date(),
        var poster_path: String = "",
        @PrimaryKey(autoGenerate = true)
        var response_order: Int = 0
)