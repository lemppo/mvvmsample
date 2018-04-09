package ru.touchin.mvvmsample.di.global.modules

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import ru.touchin.kotlinsamples.data.database.AppDatabase
import ru.touchin.mvvmsample.MvvmSampleApp

@Module
class ApplicationModule(private val application: MvvmSampleApp) {

    @Provides
    fun provideApplication(): Application = application

    @Provides
    fun provideContext(): Context = application

    @Provides
    fun provideDatabase(): AppDatabase = Room.databaseBuilder(application,
            AppDatabase::class.java, "android-kotlin-samples-db").build()
}
