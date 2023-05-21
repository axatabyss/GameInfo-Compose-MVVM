package com.example.gameinfocompose.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gameinfocompose.R
import com.example.gameinfocompose.ui.component.ErrorButton
import com.example.gameinfocompose.ui.component.LoadingCircular
import com.example.gameinfocompose.ui.component.ProductHeader
import com.example.gameinfocompose.ui.component.ProductImageCarousel
import com.example.gameinfocompose.utils.Response
import com.example.gameinfocompose.viewmodel.DetailViewModel
import com.ramcosta.composedestinations.annotation.Destination


@Destination
@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel = hiltViewModel(),
    id: Int = -1,
) {

    fun launch() {
        detailViewModel.getDetailGames(id)
    }

    launch()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        when(val gamesResponse = detailViewModel.gamesState.value){

            // Loading
            is Response.Loading -> {
                LoadingCircular(
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Error
            is Response.Failure -> {
                ErrorButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.error_message),
                    onClick = {
                        launch()
                    }
                )
            }

            // Success
            is Response.Success -> {

                val scrollState = rememberScrollState()
                val name = gamesResponse.data?.name ?: ""
                val imageUrl = gamesResponse.data?.backgroundImage ?: ""
                val releaseDate = gamesResponse.data?.released ?: ""
                val description = HtmlCompat
                    .fromHtml(gamesResponse.data?.description ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT)
                    .toString()
                val listImageCarousel = mutableListOf<String>()
                gamesResponse.data?.backgroundImage?.let {
                    listImageCarousel.add(it)
                }
                gamesResponse.data?.backgroundImageAdditional?.let {
                    listImageCarousel.add(it)
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                ) {
                    ProductHeader(
                        modifier = Modifier.padding(16.dp),
                        imageUrl = imageUrl,
                        name = name,
                        releaseDate = releaseDate,
                    )
                    ProductImageCarousel(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(),
                        listImage = listImageCarousel
                    )
                    Text(
                        text = description,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                    )
                }
            }
        }
    }









}