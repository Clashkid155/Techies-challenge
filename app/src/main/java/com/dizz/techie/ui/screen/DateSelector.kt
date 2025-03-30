package com.dizz.techie.ui.screen

import android.os.Build.VERSION.SDK_INT
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.layout.Layout
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
import com.dizz.techie.ui.ScalableYellowButton
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
    var nextPage by rememberSaveable {
        mutableStateOf(false)
    }
    var button by remember { mutableStateOf(SelectedButton.None) }
    var buttonPressed by remember { mutableStateOf(false) }
    var daySelected by rememberSaveable {
        mutableIntStateOf(0)
    }

    val buttonScaleAnimation =
        animateFloatAsState(
            if (buttonPressed) .9f else 1f,
            animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessVeryLow)
        )

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
                            rememberSharedContentState(key = "movie_logo"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            zIndexInOverlay = 1f,
                            renderInOverlayDuringTransition = false,
                            clipInOverlayDuringTransition = sharedTransitionScope.OverlayClip(
                                RoundedCornerShape(20.dp)
                            )

                        )
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

        Spacer(Modifier.height(36.dp))
        HorizontalDivider(thickness = .7.dp)
        Spacer(Modifier.height(36.dp))
        AnimatedContent(nextPage, Modifier.weight(.7f), transitionSpec = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                spring(Spring.DampingRatioNoBouncy, Spring.StiffnessMediumLow)
            ) + fadeIn() togetherWith slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                spring(Spring.DampingRatioNoBouncy, Spring.StiffnessMediumLow)
            ) + fadeOut()
        }) { pageIndex ->
            when (pageIndex) {
                false -> WhenToWatch(
                    continueButton = button,
                    daySelected = daySelected,
                    onDayChanged = { daySelected = it }) {
                    button = it
                }

                true -> WhoGoes()
            }
        }
        ScalableYellowButton(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .scale(buttonScaleAnimation.value)
                .pointerInput(Unit) {
                    detectTapGestures(onPress = {
                        if (button != SelectedButton.None) {
                            buttonPressed = true
                            tryAwaitRelease()
                            buttonPressed = false
                            nextPage = !nextPage
                        }
                    })
                }, button != SelectedButton.None
        )
        Spacer(Modifier.height(12.dp))
    }

}


@Composable
private fun WhenToWatch(
    modifier: Modifier = Modifier,
    continueButton: SelectedButton,
    daySelected: Int,
    onDayChanged: (Int) -> Unit,
    onClicked: (SelectedButton) -> Unit
) {
    Column(modifier) {
        Text("When to Watch?", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Text("Select date and time", fontSize = 12.sp, color = Color.LightGray)
        Spacer(Modifier.height(32.dp))
        DaysIndicator(daySelected) {
            onDayChanged(it)
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
                }
            }
        ) { changeScreen ->
            when (changeScreen) {
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

    }
}

@Composable
private fun WhoGoes(
    modifier: Modifier = Modifier,
) {

    var ticketAmount by remember { mutableIntStateOf(1) }
    val gifIds = remember { mutableListOf(R.drawable.smile) }
    val availableGif = listOf(R.drawable.sunglasses, R.drawable.head_blown)

    val localContext = LocalContext.current
    val gifEnabledLoader = remember {
        ImageLoader.Builder(localContext)
            .components {
                if (SDK_INT >= 28) {
                    add(AnimatedImageDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }.build()
    }


    Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        Text("Who's going?", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Text("Select ticket amount", fontSize = 12.sp, color = Color.LightGray)
        Spacer(Modifier.weight(.3f))

        Column(
            Modifier
                .fillMaxSize()
                .weight(.5f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            StackedEmojis(
                Modifier.fillMaxSize(.8f),
                overLap = .35f,
            ) {
                gifIds.forEachIndexed { index, emoji ->
                    key(index + emoji) {
                        AsyncImage(
                            model = emoji, null,
                            imageLoader = gifEnabledLoader,


                            )

                    }

                }
            }
        }

        TicketSelector(
            Modifier
                .weight(.5f)
                .align(Alignment.CenterHorizontally),
            ticketAmount,
            onMinus = {
                if (gifIds.size <= 1) return@TicketSelector
                gifIds.removeAt(gifIds.lastIndex)
                ticketAmount--
            },
            onPlus = {
                gifIds.add(availableGif.getOrElse(ticketAmount - 1) { availableGif.random() })
                ticketAmount++
            }
        )

    }
}

@Composable
fun StackedEmojis(
    modifier: Modifier = Modifier,
    overLap: Float = .3f,
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }
        val width = placeables.maxOf { it.width }
        // Adjust overlap
        val overlap = (width * overLap).toInt()

        layout(constraints.maxWidth, constraints.maxHeight) {

            placeables.forEachIndexed { index, placeable ->

                /// A few hacks to make the 3 main gifs look good
                val xPos = when (placeables.size) {
                    1 -> 0
                    2 -> ((index - (placeables.size - 1) / 2f) * overlap).toInt()
                    3 -> if (index == 2) 0 else if (index == 1) (((placeables.size - 1) / 2f) * overlap).toInt() else ((index - (placeables.size - 1) / 2f) * overlap).toInt()
                    else -> ((index - (placeables.size - 1) / 2f) * overlap).toInt()
                }

                val zIndex =
                    if (index == 0 && placeables.size == 2) {
                        1f
                    } else if (index == 3 && placeables.size == 3) {
                        1f
                    } else {
                        0f
                    }
                placeable.placeRelative(xPos, 0, zIndex)
                // println("Loop index: ${index} ------------ Placeable size: ${placeables.size} -------- zIndex: $zIndex")
            }

        }
    }
}


@Composable
fun TicketSelector(
    modifier: Modifier = Modifier,
    amount: Int,
    onMinus: () -> Unit,
    onPlus: () -> Unit
) {
    Row(
        modifier.width(250.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(
                    Color(0xFF34312F)
                )
                .clickable { onMinus() }, contentAlignment = Alignment.Center
        ) {
            Text("-", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        }

        /**
         *  Gotten from
         *  https://developer.android.com/develop/ui/compose/animation/composables-modifiers#animatedcontent
         */
        AnimatedContent(amount, transitionSpec = {
            if (targetState > initialState) {
                slideInVertically { height -> height } + fadeIn() togetherWith
                        slideOutVertically { height -> -height } + fadeOut()
            } else {
                slideInVertically { height -> -height } + fadeIn() togetherWith
                        slideOutVertically { height -> height } + fadeOut()
            }.using(
                SizeTransform(clip = false)
            )
        }, label = "Ticket selector") {
            Text(it.toString(), fontWeight = FontWeight.Bold, fontSize = 28.sp)
        }
        Box(
            Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(
                    Color(0xFF34312F),
                )
                .clickable { onPlus() }, contentAlignment = Alignment.Center
        ) {
            Text("+", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        }
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
            horizontalArrangement = Arrangement.Start,
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
    val days = rememberSaveable {
        listOf("T", "W", "T", "F")
    }
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
                        .clickable {
                            if (it != 11 && it != 15)
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
