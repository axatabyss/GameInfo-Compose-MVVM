package com.example.gameinfocompose.di

import com.example.gameinfocompose.domain.repository.GamesRepository
import com.example.gameinfocompose.network.repository.GamesRepositoryImpl
import com.example.gameinfocompose.network.service.GamesService
import com.example.gameinfocompose.utils.Const.KEY_API
import com.example.gameinfocompose.utils.Const.PAGE_SIZE
import com.example.gameinfocompose.utils.Const.WEB_API
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
class AppModules {

    @Provides
    @Named("WEB_API")
    fun provideWebAPI(): String = WEB_API

    @Provides
    @Named("KEY_API")
    fun provideKeyAPI(): String = KEY_API

    @Provides
    @Named("PAGE_SIZE")
    fun providePageSize(): Int = PAGE_SIZE

    @Provides
    fun provideRetrofit(
        @Named("WEB_API") webAPI: String,
    ): Retrofit {
        val client = OkHttpClient
            .Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
        return Retrofit.Builder()
            .baseUrl(webAPI)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun provideGamesService(
        retrofit: Retrofit
    ): GamesService = retrofit.create(GamesService::class.java)

    @Provides
    fun provideGamesRepository(
        gamesService: GamesService,
        @Named("KEY_API") keyApi: String,
        @Named("PAGE_SIZE") pageSize: Int,
    ): GamesRepository = GamesRepositoryImpl(
        gamesService = gamesService,
        pageSize = pageSize,
        apiKey = keyApi
    )

}