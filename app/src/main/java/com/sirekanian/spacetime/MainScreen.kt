package com.sirekanian.spacetime

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.sirekanian.spacetime.ui.GalleryPageContent
import com.sirekanian.spacetime.ui.ImagePageContent

@Composable
@ExperimentalPagerApi
fun MainScreen(presenter: MainPresenter) {
    val state = presenter.state
    val pages = state.getPages()
    val insets = WindowInsets.systemBars.asPaddingValues()
    HorizontalPager(
        count = pages.size,
        key = { pages[it].uuid },
    ) { index ->
        when (val page = pages[index]) {
            is ImagePage -> ImagePageContent(insets, state, page, index)
            GalleryPage -> GalleryPageContent(insets, state)
        }
    }
}
