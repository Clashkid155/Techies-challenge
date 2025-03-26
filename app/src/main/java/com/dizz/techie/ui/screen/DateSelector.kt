package com.dizz.techie.ui.screen

import android.os.Build.VERSION.SDK_INT
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import com.dizz.techie.R
import com.dizz.techie.ui.YellowButton
import com.dizz.techie.ui.theme.ActiveComponentColor
import com.dizz.techie.ui.theme.InactiveComponentColor


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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DateSelectorScreen(
    modifier: Modifier = Modifier, sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope, onCancel: () -> Unit
) {
    var nextPage by remember {
        mutableStateOf(false)
    }
    var button by remember { mutableStateOf(SelectedButton.None) }


    val buttonScaleAnimation = animateFloatAsState(if (nextPage) 2f else 1f)

    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp), horizontalAlignment = Alignment.Start
    ) {
        Icon(
            Icons.Default.Close,
            null,
            Modifier
                .align(Alignment.Start)
                .clickable { onCancel() },
            tint = Color.Gray
        )
        Spacer(Modifier.height(28.dp))
        with(sharedTransitionScope) {
            val sharedKey = rememberSharedContentState(key = "layout")
            Row(
                Modifier
                    .sharedBounds(sharedKey, animatedVisibilityScope)
                    .fillMaxWidth()
                    .height(100.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                Image(
                    painterResource(R.drawable.the_bad_guys),
                    "Movie logo",
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = "image"),
                            animatedVisibilityScope = animatedVisibilityScope,
//                            placeHolderSize = SharedTransitionScope.PlaceHolderSize.animatedSize,

                            /*   enter = expandVertically() + scaleIn(
                                   tween(delayMillis = 90),
                               ),
                               resizeMode = *//*RemeasureToBounds,*//*
                            ScaleToBounds(
                                ContentScale.None,
                                Alignment.BottomStart
                            ),*/
                            zIndexInOverlay = 1f,
                            renderInOverlayDuringTransition = false,
                            clipInOverlayDuringTransition = sharedTransitionScope.OverlayClip(
                                RoundedCornerShape(20.dp)
                            )

                        )
//                        .wrapContentWidth(Alignment.Start)

                        /* .sharedElement(sharedKey,
                             animatedVisibilityScope = animatedVisibilityScope,
                             placeHolderSize = SharedTransitionScope.PlaceHolderSize.animatedSize
                       )*/

                        /*  .sharedElement(
                              sharedKey, animatedVisibilityScope = animatedVisibilityScope,
  //                            renderInOverlayDuringTransition = false,
                              boundsTransform = { _, _ -> tween() },
                              zIndexInOverlay = 1f,
                              placeHolderSize = SharedTransitionScope.PlaceHolderSize.animatedSize

                          )*/
                        .width(120.dp)
                        .clip(
                            RoundedCornerShape(20.dp)
                        ),
                    contentScale = ContentScale.None,
                    alignment = Alignment.TopCenter
                )
                Spacer(Modifier.width(12.dp))
                TittleAndDetail(Modifier.fillMaxSize())
            }
        }

        Spacer(Modifier.height(32.dp))
        HorizontalDivider(thickness = .7.dp)
        Spacer(Modifier.height(32.dp))
        AnimatedContent(nextPage, Modifier.weight(.7f)) { pageIndex ->
            when (pageIndex) {
                false -> WhenToWatch(continueButton = button) {
                    button = it
                }

                true -> WhosGoing()
            }
        }
        YellowButton(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .scale(buttonScaleAnimation.value)
                .pointerInput(Unit) {
                    this.detectTapGestures(onPress = {

                    })
                },
            buttonText = "Continue",
            enabled = button != SelectedButton.None
        ) { nextPage = !nextPage }
        Spacer(Modifier.height(12.dp))
    }

}


@Composable
private fun WhenToWatch(
    modifier: Modifier = Modifier,
    continueButton: SelectedButton,
    onClicked: (SelectedButton) -> Unit
) {

    /*  val dateSelected by remember {
          mutableStateOf(false)
      }*/
    var daySelected by remember {
        mutableIntStateOf(0)
    }
    Column(modifier) {

        Text("When to watch?", fontSize = 20.sp)
        Text("Select date and time", fontSize = 12.sp)
        Spacer(Modifier.height(24.dp))
        DaysIndicator(daySelected) {
            daySelected = it
        }
        Spacer(Modifier.height(24.dp))

        AnimatedContent(
            daySelected != 0,
            Modifier.fillMaxHeight(.94f),
            transitionSpec = {

                if (daySelected == 0) {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Up,
                        spring(dampingRatio = 0.85f, stiffness = Spring.StiffnessVeryLow)
                    ) togetherWith
                            fadeOut()
                } else {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Up,
                        spring(dampingRatio = 0.85f)
                    ) togetherWith fadeOut()

                    /* using
                                                SizeTransform { initialSize, targetSize ->
                                                    if (targetState) {
                                                        keyframes {
                                                            // Expand horizontally first.
                                                            IntSize(targetSize.width, initialSize.height) at 150
                                                            durationMillis = 300
                                                        }
                                                    } else {
                                                        keyframes {
                                                            // Shrink vertically first.
                                                            IntSize(initialSize.width, targetSize.height) at 150
                                                            durationMillis = 300
                                                        }
                                                    }
                                                }*/
                }
            }
        ) {
            when (it) {
                false -> Text(
                    "Select a day to see the\n available showtime",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp, color = Color.LightGray
                )

                true -> TimeButtons(continueButton) { bState -> onClicked(bState) }
            }
        }
//        Spacer(Modifier.height(12.dp))

    }
}

@Composable
private fun WhosGoing(
    modifier: Modifier = Modifier,
//    continueButton: SelectedButton,
//    onClicked: (SelectedButton) -> Unit
) {
    val buttonEnabled by remember {
        mutableStateOf(false)
    }
    var daySelected by remember {
        mutableIntStateOf(0)
    }
    val gifEnabledLoader = ImageLoader.Builder(LocalContext.current)
        .components {

            if (SDK_INT >= 28) {
                add(AnimatedImageDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()
    Column(modifier, verticalArrangement = Arrangement.Top) {

        Text("Who's going?", fontSize = 20.sp)
        Text("Select ticket amount")
        Spacer(Modifier.height(24.dp))
        AsyncImage(
            model = R.drawable.head_blown, null,
            imageLoader = gifEnabledLoader, modifier = Modifier
                .size(200.dp)
                .background(Color.Red)
        )


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
        Text("2025 • Animation • 96 min", fontSize = 12.sp, color = Color.LightGray)
        Row(
            horizontalArrangement = Arrangement.Start,//Arrangement.spacedBy(8.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painterResource(R.drawable.imdb_logo), null, modifier = Modifier.size(38.dp))
            Spacer(Modifier.width(8.dp))
            Text("7.7", fontSize = 12.sp, color = Color.LightGray)
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
                            if (it != 15)
                                onClicked(it)
                        }, contentAlignment = Alignment.Center
                ) {
                    if (it == 15) Icon(
                        painterResource(R.drawable.arrow),
                        null,
                        modifier = Modifier
                            .rotate(90f)
                            .size(16.dp), tint = Color.White
                    ) else Text(
                        it.toString(),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (it == 11) Color.Gray else if (daySelected == it) Color.Black else Color.White
                    )
                }
                Spacer(Modifier.height(8.dp))
                if (it != 15) Text(
                    days[index],
                    color = if (daySelected == it) Color.White else Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}


@Composable
private fun TimeButtons(buttonState: SelectedButton, onClicked: (SelectedButton) -> Unit) {
    Column {
        TimeButton(
            "10:45 AM",
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable(null, null) {

                    onClicked(SelectedButton.setValue(buttonState, SelectedButton.First))
                },
            sizing = 1.8f,
            onTapped = buttonState == SelectedButton.First
        )
        Spacer(Modifier.height(8.dp))
        TimeButton(
            "03:45 PM",
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable(null, null) {
                    onClicked(SelectedButton.setValue(buttonState, SelectedButton.Second))
                },
            onTapped = buttonState == SelectedButton.Second
        )
        Spacer(Modifier.height(8.dp))
        TimeButton(
            "09:00 PM",
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable(null, null) {
                    onClicked(SelectedButton.setValue(buttonState, SelectedButton.Third))
                },
            sizing = 1.5f,
            onTapped = buttonState == SelectedButton.Third
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
            TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (onTapped) Color.Black else Color.White
            )
        )
        val textSize = textLayout.size
        // Use 9.4% of the width
        val textPosition = width - (width * 0.94f)
        drawText(textLayout, topLeft = Offset(textPosition, height / 2 - textSize.height / 2f))
    }
}

/*
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DateSelectorPreview() {
    Techie_1Theme {
        DateSelectorScreen(){}
    }
}*/
