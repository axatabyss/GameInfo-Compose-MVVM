package com.example.gameinfocompose.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.gameinfocompose.R
import com.example.gameinfocompose.ui.destinations.DetailScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    id: Int = -1,
    name: String = "",
    imageUrl: String = "",
    releaseDate: String = "",
    navigator: DestinationsNavigator,
) {
    val imagePainter = rememberAsyncImagePainter(
        model = imageUrl,
        error = painterResource(id = R.drawable.ic_launcher_foreground),
    )
    Card(
        onClick = {

                  navigator.navigate(
                      DetailScreenDestination(
                          id = id
                      )
                  )

        },
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colors.secondary, CircleShape)
            )
            Text(
                text = name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = releaseDate,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 8.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}