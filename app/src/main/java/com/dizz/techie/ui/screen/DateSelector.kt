package com.dizz.techie.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dizz.techie.R
import com.dizz.techie.ui.YellowButton
import com.dizz.techie.ui.theme.ActiveComponentColor
import com.dizz.techie.ui.theme.InactiveComponentColor
import com.dizz.techie.ui.theme.Techie_1Theme


private enum class SelectedButton {
    First,
    Second,
    Third,
    None;

    companion object {
        fun setValue(previousValue: SelectedButton, value: SelectedButton): SelectedButton {
            if (previousValue == value) return None

            return value
        }
    }
}

@Composable
fun DateSelectorScreen(modifier: Modifier = Modifier) {
    val buttonEnabled by remember {
        mutableStateOf(false)
    }
    /*  val dateSelected by remember {
          mutableStateOf(false)
      }*/
    var daySelected by remember {
        mutableIntStateOf(0)
    }
    Column(
        modifier
            .systemBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
            .padding(horizontal = 12.dp), horizontalAlignment = Alignment.Start
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painterResource(R.drawable.the_bad_guys),
                "Movie logo",
                modifier = Modifier
                    .size(120.dp, 100.dp)
                    .clip(
                        RoundedCornerShape(20.dp)
                    ),
                contentScale = ContentScale.None,
                alignment = Alignment.TopCenter
            )
            Spacer(Modifier.width(12.dp))
            TittleAndDetail(Modifier.height(100.dp))
        }
        Spacer(Modifier.height(24.dp))
        HorizontalDivider()
        Spacer(Modifier.height(24.dp))
        Text("When to watch?")
        Text("Select date and time")
        Spacer(Modifier.height(24.dp))
        DaysIndicator(daySelected) {
            daySelected = it
        }
        Spacer(Modifier.height(24.dp))

        AnimatedContent(
            daySelected != 0,
            Modifier.weight(.5f)
        ) {
            when (it) {
                false -> Text(
                    "Select a day to see the\n available showtime",
                    modifier = Modifier.fillMaxWidth()
//                        .weight(.5f)
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )

                true -> TimeButtons()
            }
        }
        Spacer(Modifier.height(12.dp))

        YellowButton(
            Modifier
                .fillMaxWidth()
                .height(50.dp),
            buttonText = "Continue",
            enabled = buttonEnabled
        ) { }
        Spacer(Modifier.height(12.dp))
    }
}

@Composable
private fun TittleAndDetail(modifier: Modifier = Modifier) {
    Column(modifier) {
        Image(
            painterResource(R.drawable.the_bad_guys_text),
            "Movie title",
            modifier = Modifier.size(48.dp),
        )
        Spacer(Modifier.height(4.dp))
        Text("2025 • Animation • 96 min", fontSize = 14.sp)
        Row(
            horizontalArrangement = Arrangement.Start,//Arrangement.spacedBy(8.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painterResource(R.drawable.imdb_logo), null, modifier = Modifier.size(38.dp))
            Text("7.7", fontSize = 14.sp)
        }
    }
}

@Composable
private fun DaysIndicator(daySelected: Int, onClicked: (Int) -> Unit) {
    val days = listOf("T", "W", "T", "F")
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        (11..15).forEachIndexed { index, it ->
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(
                            if (daySelected == it) ActiveComponentColor else InactiveComponentColor
                        )
                        .clickable { /*daySelected = it */
                            onClicked(it)
                        }, contentAlignment = Alignment.Center
                ) {
                    if (it == 15) Icon(
                        painterResource(R.drawable.arrow),
                        null,
                        modifier = Modifier
                            .rotate(90f)
                            .size(16.dp), tint = Color.White
                    ) else Text(it.toString(), color = Color.White)
                }
                Spacer(Modifier.height(8.dp))
                if (it != 15) Text(days[index])
            }
        }
    }
}


@Composable
fun TimeButtons() {

    var button by remember { mutableStateOf(SelectedButton.None) }
    /*  Spacer(
          Modifier
              .fillMaxWidth()
              .height(50.dp)
              .drawBehind {
                  val width = size.width
                  val height = size.height
                  val cornerRadius = CornerRadius(100f, 100f)
                  val cornerRadiusMin = CornerRadius(10f, 10f)

                  val secondDrawFraction = 3.5f
                  val secondWidth = width - (width / secondDrawFraction)
                  val gap = 20f

                  *//* drawRoundRect(Color.Green, size = Size(width / 2, height))
                 *//**//*     val secondDrawFraction = 3.5f
             val secondWidth = width - (width / secondDrawFraction)
             drawRoundRect(
                 Color.Blue,
                 size = Size(width / secondDrawFraction, height),
                 topLeft = Offset(secondWidth, 0f),
                 cornerRadius = CornerRadius(50f, 50f)
             ) *//**//*

        drawRoundRect(
            Color.Blue,
            size = Size(width / secondDrawFraction, height),
            topLeft = Offset(secondWidth, 0f),
        )*//*

                val firstPath = Path().apply {
                    addRoundRect(
                        RoundRect(
                            rect = Rect(
                                size = Size(secondWidth - gap, height),
                                offset = Offset.Zero,

                                ),
                            topLeft = cornerRadius,
                            bottomLeft = cornerRadius,
                            topRight = cornerRadiusMin,
                            bottomRight = cornerRadiusMin
                        )
                    )
                }
                drawPath(firstPath, ActiveComponentColor)
                val secondPath = Path().apply {
                    addRoundRect(
                        RoundRect(
                            rect = Rect(
                                size = Size(width / secondDrawFraction, height),
                                offset = Offset(secondWidth, 0f),

                                ),
                            topRight = cornerRadius,
                            bottomRight = cornerRadius,
                            topLeft = cornerRadiusMin,
                            bottomLeft = cornerRadiusMin
                        )
                    )
                }
                drawPath(secondPath, InactiveComponentColor)

            })*/
    Column {
        TimeButton(
            "10:45 AM",
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable(null, null) {
                    button = SelectedButton.setValue(button, SelectedButton.First)
                },
            sizing = 1.8f,
            onTapped = button == SelectedButton.First
        )
        Spacer(Modifier.height(8.dp))

        TimeButton(
            "03:45 AM",
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable(null, null) {
                    button = SelectedButton.setValue(button, SelectedButton.Second)
                },
            onTapped = button == SelectedButton.Second
        )
        Spacer(Modifier.height(8.dp))

        TimeButton(
            "09:00 PM",
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable(null, null) {
                    button = SelectedButton.setValue(button, SelectedButton.Third)
                },
            sizing = 1.5f,
            onTapped = button == SelectedButton.Third
        )
    }
}

@Composable
fun TimeButton(
    text: String,
    modifier: Modifier = Modifier,
    sizing: Float = 3.5f,
    onTapped: Boolean = false
) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(
        modifier

    ) {
        val width = size.width
        val height = size.height
        val cornerRadius = CornerRadius(100f, 100f)
        val cornerRadiusMin = CornerRadius(10f, 10f)

        val secondWidth = width - (width / sizing)
        val gap = 15f

        val firstPath = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(
                        size = Size(secondWidth - gap, height),
                        offset = Offset.Zero,

                        ),
                    topLeft = cornerRadius,
                    bottomLeft = cornerRadius,
                    topRight = cornerRadiusMin,
                    bottomRight = cornerRadiusMin
                )
            )
        }
        drawPath(firstPath, if (onTapped) Color.White else InactiveComponentColor)
        val secondPath = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(
                        size = Size(width / sizing, height),
                        offset = Offset(secondWidth, 0f),

                        ),
                    topRight = cornerRadius,
                    bottomRight = cornerRadius,
                    topLeft = cornerRadiusMin,
                    bottomLeft = cornerRadiusMin
                )
            )
        }
        drawPath(secondPath, InactiveComponentColor)
        val textLayout = textMeasurer.measure(
            text,
            TextStyle(fontSize = 16.sp, color = if (onTapped) Color.Black else Color.White)
        )
        val textSize = textLayout.size
        // Use 9.4% of the width
        val textPosition = width - (width * 0.94f)
        drawText(textLayout, topLeft = Offset(textPosition, height / 2 - textSize.height / 2f))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DateSelectorPreview() {
    Techie_1Theme {
        DateSelectorScreen()
    }
}