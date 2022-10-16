package com.sirekanian.spacetime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.sirekanian.spacetime.data.Repository
import com.sirekanian.spacetime.data.api.ThumbnailApi
import com.sirekanian.spacetime.ext.app
import com.sirekanian.spacetime.model.ImagePage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberMainPresenter(): MainPresenter {
    val app = LocalContext.current.app()
    val scope = rememberCoroutineScope()
    return remember { MainPresenterImpl(app.api, app.repository, scope) }
}

interface MainPresenter {

    val state: MainState
    fun savePage(page: ImagePage)
    fun removePage(page: ImagePage)
    fun loadGallery()

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
        scope.launch {
            repository.savePage(page)
            updatePages()
        }
    }

    override fun removePage(page: ImagePage) {
        scope.launch {
            repository.removePage(page)
            updatePages()
        }
    }

    override fun loadGallery() {
        scope.launch {
            state.thumbnails = state.thumbnails + api.getThumbnails(state.nextDate)
        }
    }

    private suspend fun updatePages() {
        state.pages = repository.getPages()
    }

}