package com.sirekanian.spacetime

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.sirekanian.spacetime.data.Repository
import com.sirekanian.spacetime.data.api.ThumbnailApi
import com.sirekanian.spacetime.ext.app
import com.sirekanian.spacetime.model.GalleryPage
import com.sirekanian.spacetime.model.ImagePage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.UnknownHostException

@Composable
fun rememberMainPresenter(): MainPresenter {
    val app = LocalContext.current.app()
    val scope = rememberCoroutineScope {
        CoroutineExceptionHandler { _, throwable ->
            if (throwable is UnknownHostException) {
                Log.e("SPACETIME", "Uncaught exception: ${throwable.message}")
            } else {
                Log.e("SPACETIME", "Uncaught exception", throwable)
            }
        }
    }
    return remember { MainPresenterImpl(app.api, app.repository, scope) }
}

interface MainPresenter {

    val state: MainState
    fun savePage(page: ImagePage)
    fun removePage(page: ImagePage)
    fun loadGallery()
    fun openPageByIndex(index: Int)

}

class MainPresenterImpl(
    private val api: ThumbnailApi,
    private val repository: Repository,
    private val scope: CoroutineScope,
) : MainPresenter {

    override val state = MainState()

    init {
        scope.launch {
            updatePages()
        }
    }

    override fun savePage(page: ImagePage) {
        state.editablePage = null
        scope.launch {
            repository.savePage(page)
            updatePages()
        }
    }

    override fun removePage(page: ImagePage) {
        state.editablePage = null
        scope.launch {
            repository.removePage(page)
            updatePages()
        }
    }

    override fun loadGallery() {
        scope.launch {
            try {
                state.thumbnails = state.thumbnails.orEmpty() + api.getThumbnails(state.nextDate)
            } catch (exception: Exception) {
                Log.d("Spacetime", "Cannot load thumbnails", exception)
                state.thumbnails?.let { thumbnails ->
                    if (thumbnails.isEmpty()) {
                        state.thumbnails = null
                    }
                }
            }
        }
    }

    override fun openPageByIndex(index: Int) {
        scope.launch {
            state.pagerState.animateScrollToPage(index)
        }
    }

    private suspend fun updatePages() {
        state.pages = repository.getPages().plus(GalleryPage)
    }

}