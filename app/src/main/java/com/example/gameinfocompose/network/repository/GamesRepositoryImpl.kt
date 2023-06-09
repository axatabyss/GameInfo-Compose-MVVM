package com.example.gameinfocompose.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.gameinfocompose.domain.model.Games
import com.example.gameinfocompose.domain.repository.GamesRepository
import com.example.gameinfocompose.network.pagingsource.GamesPagingSource
import com.example.gameinfocompose.network.service.GamesService
import com.example.gameinfocompose.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GamesRepositoryImpl @Inject constructor(
    private val gamesService: GamesService,
    private val pageSize: Int,
    private val apiKey: String,
): GamesRepository {
    override fun getAllGames(): Flow<PagingData<Games>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            GamesPagingSource(
                response = { pageNext ->
                    gamesService.getAllGames(
                        key = apiKey,
                        page = pageNext,
                        pageSize = pageSize,
                    )
                }
            )
        }
    ).flow

    override fun getDetailGames(id: Int): Flow<Response<Games>> = flow{
        try {
            emit(Response.Loading)
            val responseApi = gamesService.getGamesDetail(
                key = apiKey,
                id = id
            )
            emit(Response.Success(responseApi))
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}