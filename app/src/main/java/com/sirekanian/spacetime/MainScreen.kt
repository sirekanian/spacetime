package com.sirekanian.spacetime

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
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
    BackHandler(state.pagerState.currentPage > 0) {
        presenter.openPageByIndex(0)
    }
    val pages = state.pages.plus(GalleryPage)
    val insets = WindowInsets.systemBars.asPaddingValues()
    HorizontalPager(
        state = state.pagerState,
        count = pages.size,
        key = { pages[it].id },
        userScrollEnabled = state.editablePage == null,
    ) { index ->
        when (val page = pages[index]) {
            is ImagePage -> {
                ImagePageContent(
                    insets,
                    state,
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
