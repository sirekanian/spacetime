package com.sirekanian.spacetime

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.runtime.Composable
import com.sirekanian.spacetime.ext.ScreenAnimatedVisibility
import com.sirekanian.spacetime.model.EditablePage
import com.sirekanian.spacetime.model.EditablePage.Autofocus
import com.sirekanian.spacetime.model.GalleryPage
import com.sirekanian.spacetime.model.ImagePage
import com.sirekanian.spacetime.model.createImagePage
import com.sirekanian.spacetime.ui.DraftPage
import com.sirekanian.spacetime.ui.GalleryErrorContent
import com.sirekanian.spacetime.ui.GalleryPageContent
import com.sirekanian.spacetime.ui.ImagePageContent

@Composable
@ExperimentalFoundationApi
fun MainScreen(presenter: MainPresenter) {
    val state = presenter.state
    BackHandler(state.pagerState.currentPage > 0) {
        presenter.openPageByIndex(0)
    }
    val pages = state.pages
    val insets = WindowInsets.systemBars.asPaddingValues()
    HorizontalPager(
        state = state.pagerState,
        pageCount = pages.size,
        key = { pages[it].id },
        userScrollEnabled = state.editablePage == null,
    ) { index ->
        when (val page = pages[index]) {
            is ImagePage -> {
                ImagePageContent(
                    insets,
                    page,
                    state.editablePage,
                    onEdit = { state.editablePage = it },
                    onClose = { state.editablePage = null },
                    onDelete = { presenter.removePage(page) },
                    onDone = { presenter.savePage(it) }
                )
            }
            GalleryPage -> {
                state.thumbnails?.let { thumbnails ->
                    GalleryPageContent(
                        insets = insets,
                        thumbnails = thumbnails,
                        onSelect = { url ->
                            val draft = createImagePage("", url, "", 0.5f)
                            state.editablePage = EditablePage(draft, Autofocus.NAME)
                        },
                        onEnd = { presenter.loadGallery() },
                    )
                } ?: run {
                    GalleryErrorContent(onRetry = presenter::loadGallery)
                }
            }
        }
    }
    ScreenAnimatedVisibility(visible = state.draft != null) {
        state.draft?.let { draft ->
            DraftPage(
                insets = insets,
                editablePage = draft,
                onClose = { state.editablePage = null },
                onDone = { presenter.savePage(it) },
            )
        }
    }
}
