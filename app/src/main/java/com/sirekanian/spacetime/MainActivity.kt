package com.sirekanian.spacetime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.sirekanian.spacetime.ui.GalleryPageContent
import com.sirekanian.spacetime.ui.ImagePageContent
import com.sirekanian.spacetime.ui.theme.SpacetimeTheme

class MainActivity : ComponentActivity() {

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val state = remember { MainState() }
            val pages = state.getPages()
            SpacetimeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    HorizontalPager(
                        count = pages.size,
                        modifier = Modifier.systemBarsPadding(),
                    ) { index ->
                        when (val page = pages[index]) {
                            is ImagePage -> ImagePageContent(state, page, index)
                            GalleryPage -> GalleryPageContent(state)
                        }
                    }
                }
            }
        }
    }

}
