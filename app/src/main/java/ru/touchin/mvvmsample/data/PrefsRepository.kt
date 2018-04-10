package ru.touchin.mvvmsample.data

import android.content.Context
import ru.touchin.mvvmsample.R
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: Oksana Pokrovskaya
 * Email: lempo.developer@gmail.com
 */
@Singleton
class PrefsRepository @Inject constructor(private val context: Context) {
    private val prefs by lazy {
        context.getSharedPreferences(context.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE)
    }

    private val modeKey by lazy { context.getString(R.string.preference_mode_key) }
    private val pageKey by lazy { context.getString(R.string.preference_page_key) }
    private val totalPagesKey by lazy { context.getString(R.string.preference_total_pages_key) }
    private val baseUrlKey by lazy { context.getString(R.string.preference_base_url_key) }

    var mode: Int = MoviesRepository.POPULAR
        get() = prefs.getInt(modeKey, MoviesRepository.POPULAR)
        set(value) {
            prefs.edit().apply {
                putInt(modeKey, value)
                apply()
            }
            field = value
        }

    var page: Int = 0
        get() = prefs.getInt(pageKey, 0)
        set(value) {
            prefs.edit().apply {
                putInt(pageKey, value)
                apply()
            }
            field = value
        }

    var totalPages: Int = 2
        get() = prefs.getInt(totalPagesKey, 2)
        set(value) {
            prefs.edit().apply {
                putInt(totalPagesKey, value)
                apply()
            }
            field = value
        }

    var imageBaseUrl: String = ""
        get() = prefs.getString(baseUrlKey, "")
        set(value) {
            prefs.edit().apply {
                putString(baseUrlKey, value)
                apply()
            }
            field = value
        }
}