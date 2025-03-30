package com.dizz.techie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dizz.techie.ui.screen.DateSelectorScreen
import com.dizz.techie.ui.screen.NowShowingScreen
import com.dizz.techie.ui.theme.Techie_1Theme

//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            Techie_1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Start(
                        Modifier
                            .fillMaxSize()
                            .padding(
                                top = innerPadding.calculateTopPadding(),
                                bottom = innerPadding.calculateBottomPadding()
                            )
                    )

                }

            }

        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Start(modifier: Modifier = Modifier) {
    var firstScreen by rememberSaveable { mutableStateOf(true) }
    SharedTransitionLayout {
        AnimatedContent(firstScreen, modifier, transitionSpec = {
            slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Up) togetherWith slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
            )
        }) { changeScreen ->
            when (changeScreen) {
                true -> NowShowingScreen(
                    animatedVisibilityScope = this@AnimatedContent,
                    sharedTransitionScope = this@SharedTransitionLayout
                ) { firstScreen = false }

                false -> DateSelectorScreen(
                    animatedVisibilityScope = this@AnimatedContent,
                    sharedTransitionScope = this@SharedTransitionLayout
                ) { firstScreen = true }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    Techie_1Theme {
        Start(Modifier.fillMaxSize())
    }
}