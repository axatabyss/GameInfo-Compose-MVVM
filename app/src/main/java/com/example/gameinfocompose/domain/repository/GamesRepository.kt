package com.example.gameinfocompose.domain.repository

import androidx.paging.PagingData
import com.example.gameinfocompose.domain.model.Games
import kotlinx.coroutines.flow.Flow
import com.example.gameinfocompose.utils.Response


interface GamesRepository {

    fun getAllGames(): Flow<PagingData<Games>>
    fun getDetailGames(id: Int): Flow<Response<Games>>

}