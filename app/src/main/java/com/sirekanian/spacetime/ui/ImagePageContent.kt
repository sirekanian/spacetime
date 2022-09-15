package com.sirekanian.spacetime.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sirekanian.spacetime.ImagePage
import com.sirekanian.spacetime.MainState

@Composable
fun ImagePageContent(state: MainState, page: ImagePage, index: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(text = page.name)
        Button(onClick = { state.removePage(index) }) {
            Text("Delete")
        }
    }
}
