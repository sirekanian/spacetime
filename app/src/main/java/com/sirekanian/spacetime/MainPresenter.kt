package com.sirekanian.spacetime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.sirekanian.spacetime.data.Repository
import com.sirekanian.spacetime.ext.app

@Composable
fun rememberMainPresenter(): MainPresenter {
    val context = LocalContext.current
    return remember { MainPresenterImpl(context.app().repository) }
}

interface MainPresenter {

    val state: MainState

}

class MainPresenterImpl(private val repository: Repository) : MainPresenter {

    override val state = MainState()

}