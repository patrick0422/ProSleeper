package com.patrick0422.prosleeper.di

import android.content.Context
import androidx.room.Room
import com.patrick0422.prosleeper.data.local.WakeUpTimeDatabase
import com.patrick0422.prosleeper.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWakeUpTimeDataBase(@ApplicationContext context: Context): WakeUpTimeDatabase =
        Room.databaseBuilder(context, WakeUpTimeDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideWakeUpTimeDao(database: WakeUpTimeDatabase) = database.wakeUpTimeDao()
}