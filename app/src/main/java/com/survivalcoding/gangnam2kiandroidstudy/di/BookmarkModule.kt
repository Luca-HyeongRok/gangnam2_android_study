package com.survivalcoding.gangnam2kiandroidstudy.di

import androidx.room.Room
import com.survivalcoding.gangnam2kiandroidstudy.BuildConfig
import com.survivalcoding.gangnam2kiandroidstudy.data.bookmark.dao.BookmarkDao
import com.survivalcoding.gangnam2kiandroidstudy.data.bookmark.local.AppDatabase
import com.survivalcoding.gangnam2kiandroidstudy.data.bookmark.repository.InMemoryBookmarkRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.bookmark.repository.RoomBookmarkRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.BookmarkRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.use_case.AddBookmarkUseCase
import com.survivalcoding.gangnam2kiandroidstudy.domain.use_case.GetBookmarkedRecipeIdsUseCase
import com.survivalcoding.gangnam2kiandroidstudy.domain.use_case.RemoveBookmarkUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val bookmarkModule = module {

    // AppDatabase
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "bookmark_db"
        ).build()
    }

    // BookmarkDao
    single {
        get<AppDatabase>().bookmarkDao()
    }

    // BookmarkRepository
    single<BookmarkRepository> {
        if (BuildConfig.DEBUG) {
            // Debug builds: In-memory repository
            InMemoryBookmarkRepositoryImpl()
        } else {
            // Release builds: Room-based repository
            RoomBookmarkRepositoryImpl(get<BookmarkDao>())
        }
    }

    // Use Cases
    single {
        AddBookmarkUseCase(bookmarkRepository = get())
    }

    single {
        GetBookmarkedRecipeIdsUseCase(bookmarkRepository = get())
    }

    single {
        RemoveBookmarkUseCase(bookmarkRepository = get())
    }
}
