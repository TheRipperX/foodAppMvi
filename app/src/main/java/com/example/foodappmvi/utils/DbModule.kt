package com.example.foodappmvi.utils

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodappmvi.data.db.FoodDataBase
import com.example.foodappmvi.data.db.FoodEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun proDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        FoodDataBase::class.java,
        DATABASE_NAME
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun proDao(db: FoodDataBase) = db.foodDao()

    @Provides
    @Singleton
    fun proEntity() = FoodEntity()

}