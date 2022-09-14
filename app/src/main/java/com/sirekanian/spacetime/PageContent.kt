package com.sirekanian.spacetime

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PageContent(state: MainState, page: Page, index: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when (page) {
            is ImagePage -> {
                Text(text = "page ${page.name}")
                Button(onClick = { state.removePage(index) }) {
                    Text("Delete")
                }
            }
            GalleryPage -> {
                var name by remember { mutableStateOf("") }
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = { state.addPage(name) },
                    enabled = name.isNotEmpty()
                ) {
                    Text("Add")
                }
            }
        }
    }
}
