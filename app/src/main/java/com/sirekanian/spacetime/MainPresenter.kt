package com.sirekanian.spacetime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.sirekanian.spacetime.data.Repository
import com.sirekanian.spacetime.ext.app
import com.sirekanian.spacetime.model.GalleryPage
import com.sirekanian.spacetime.model.ImagePage
import com.sirekanian.spacetime.model.Page
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun rememberMainPresenter(): MainPresenter {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    return remember { MainPresenterImpl(context.app().repository, scope) }
}

interface MainPresenter {

    fun observePages(): Flow<List<Page>>
    fun addPage(page: ImagePage)
    fun removePage(page: ImagePage)

}

class MainPresenterImpl(
    private val repository: Repository,
    private val scope: CoroutineScope,
) : MainPresenter {

    override fun observePages(): Flow<List<Page>> =
        repository.observePages().map { it.plus(GalleryPage) }

    override fun addPage(page: ImagePage) {
        scope.launch {
            repository.addPage(page)
        }
    }

    override fun removePage(page: ImagePage) {
        scope.launch {
            repository.removePage(page)
        }
    }

}