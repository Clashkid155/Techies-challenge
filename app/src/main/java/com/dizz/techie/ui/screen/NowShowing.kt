package com.dizz.techie.ui.screen

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
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dizz.techie.R
import com.dizz.techie.ui.YellowButton
import com.dizz.techie.ui.theme.Techie_1Theme

@Composable
fun NowShowingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(horizontal = 12.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text("Now showing")
            Spacer(Modifier.width(8.dp))
//            Icon(Icons.Default.ArrowDropDown, null)
            Icon(
                painterResource(R.drawable.arrow),
                null,
                modifier = Modifier
                    .rotate(90f)
                    .size(12.dp).align(Alignment.CenterVertically)
            )
            Spacer(Modifier.weight(0.8f))
            Icon(Icons.Default.Face, null)
        }
        Spacer(Modifier.height(12.dp))
        Image(
            painterResource(R.drawable.the_bad_guys),
            "Movie image",
            modifier = Modifier
                .fillMaxWidth()
                .weight(.5f)
                .clip(RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp)),
            contentScale = ContentScale.FillBounds
        )
        Text("2025 • Animation • 96 min")
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painterResource(R.drawable.imdb_logo), null, modifier = Modifier.size(45.dp))
            Text("7.7")
        }
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            YellowButton(Modifier.width(60.dp), "Buy Tickets") { }
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
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    Techie_1Theme {
        NowShowingScreen()
    }
}
