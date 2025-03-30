package com.dizz.techie.ui.screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dizz.techie.R
import com.dizz.techie.ui.YellowButton

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NowShowingScreen(
    modifier: Modifier = Modifier, sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope, onBuy: () -> Unit
) {
    with(sharedTransitionScope) {
        val sharedKey = rememberSharedContentState(key = "layout")

        Column(
            modifier
                .fillMaxSize()
                .sharedBounds(sharedKey, animatedVisibilityScope)

                .padding(horizontal = 12.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Now showing", fontSize = 20.sp)
                Spacer(Modifier.width(8.dp))
                Icon(
                    painterResource(R.drawable.arrow),
                    null,
                    modifier = Modifier
                        .rotate(90f)
                        .size(12.dp)
                )
                Spacer(Modifier.weight(0.8f))
                Icon(Icons.Default.Face, null)
            }
            Spacer(Modifier.height(16.dp))

            Image(
                painterResource(R.drawable.the_bad_guys),
                "Movie image",
                modifier = Modifier
                    .sharedElement(
                        rememberSharedContentState(key = "movie_logo"),
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
                    .fillMaxWidth()
                    .weight(.5f)
                    .clip(RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp)),
                contentScale = ContentScale.FillBounds
            )
            Text("2025 • Animation • 96 min", fontSize = 14.sp, color = Color.LightGray)
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painterResource(R.drawable.imdb_logo), null, modifier = Modifier.size(40.dp))
                Text("7.7", fontSize = 14.sp, color = Color.LightGray)
            }
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                YellowButton(Modifier.height(50.dp), "Buy Tickets") {
                    onBuy()
                }
                Spacer(Modifier.width(12.dp))
                Box(
                    Modifier
                        .size(50.dp)
                        .background(
                            Color(0xFF34312F), CircleShape
                        ), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.PlayArrow, null, tint = Color.White
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
        }
    }

}


/*@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    Techie_1Theme {
        NowShowingScreen(){}
    }
}*/
