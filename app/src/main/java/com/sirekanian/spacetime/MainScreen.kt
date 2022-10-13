package com.sirekanian.spacetime

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.sirekanian.spacetime.model.Draft
import com.sirekanian.spacetime.model.GalleryPage
import com.sirekanian.spacetime.model.ImagePage
import com.sirekanian.spacetime.ui.DraftAlertDialog
import com.sirekanian.spacetime.ui.GalleryPageContent
import com.sirekanian.spacetime.ui.ImagePageContent

@Composable
@ExperimentalPagerApi
fun MainScreen(presenter: MainPresenter) {
    val state = presenter.state
    val pages by presenter.observePages().collectAsState(listOf())
    val insets = WindowInsets.systemBars.asPaddingValues()
    HorizontalPager(
        count = pages.size,
        key = { pages[it].id },
    ) { index ->
        when (val page = pages[index]) {
            is ImagePage -> {
                ImagePageContent(
                    insets,
                    page,
                    onDelete = { presenter.removePage(page) },
                    onDone = { presenter.savePage(it) }
                )
            }
            GalleryPage -> {
                GalleryPageContent(
                    insets,
                    state.thumbnails,
                    onSelect = { state.draft = Draft(it) },
                    onEnd = { presenter.loadGallery() }
                )
            }
        }
    }
    DraftAlertDialog(state, onConfirm = { presenter.savePage(it) })
}
