package com.example.home_data.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.example.home_data.alarm.AlarmHandlerImpl
import com.example.home_data.local.HomeDao
import com.example.home_data.local.HomeDatabase
import com.example.home_data.local.typeconverter.HomeTypeConverter
import com.example.home_data.remote.HomeApi
import com.example.home_data.repository.HomeRepositoryImpl
import com.example.home_domain.alarm.AlarmHandler
import com.example.home_domain.detail.usecase.GetHabitByIdUseCase
import com.example.home_domain.detail.usecase.InsertHabitUseCase
import com.example.home_domain.home.usecase.CompletHabitUseCase
import com.example.home_domain.home.usecase.GetHabitsForDateUseCase
import com.example.home_domain.home.usecase.SyncHabitUseCase
import com.example.home_domain.repository.HomeRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val HOME_DATABASE = "home.db"

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Singleton
    @Provides
    fun provideHomeRepository(dao: HomeDao, api: HomeApi, alarmHandler: AlarmHandler, workManager: WorkManager): com.example.home_domain.repository.HomeRepository {
        return HomeRepositoryImpl(
            dao,
            api,
            alarmHandler,
            workManager
        )
    }

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context, gson: Gson) =
        Room.databaseBuilder(context, HomeDatabase::class.java, HOME_DATABASE)
            .addTypeConverter(HomeTypeConverter(gson)).build()

    @Singleton
    @Provides
    fun provideUserDao(db: HomeDatabase) = db.homeDao

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideAlarmHandler(@ApplicationContext context: Context): AlarmHandler {
        return AlarmHandlerImpl(context)
    }

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
    }

    @Singleton
    @Provides
    fun provideHomeApi(client: OkHttpClient): HomeApi {
        return Retrofit.Builder().baseUrl(HomeApi.BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HomeApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideGetHabitByIdUseCase(homeRepository: HomeRepository): GetHabitByIdUseCase {
        return GetHabitByIdUseCase(homeRepository)
    }

    @Singleton
    @Provides
    fun provideInsertHabitUseCase(homeRepository: HomeRepository): InsertHabitUseCase {
        return InsertHabitUseCase(homeRepository)
    }

    @Singleton
    @Provides
    fun provideCompletHabitUseCase(homeRepository: HomeRepository): CompletHabitUseCase {
        return CompletHabitUseCase(homeRepository)
    }

    @Singleton
    @Provides
    fun provideSyncHabitUseCase(homeRepository: HomeRepository): SyncHabitUseCase {
        return SyncHabitUseCase(homeRepository)
    }

    @Singleton
    @Provides
    fun provideGetHabitsForDateUseCase(homeRepository: HomeRepository): GetHabitsForDateUseCase {
        return GetHabitsForDateUseCase(homeRepository)
    }

}