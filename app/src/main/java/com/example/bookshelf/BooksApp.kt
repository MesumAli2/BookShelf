package com.example.bookshelf

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf.ui.screens.BooksViewModel
import com.example.bookshelf.ui.screens.HomeScreen

@Composable
fun BooksApp (){

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
        TopAppBar(
            title = {
            Text(text = "Book Shelf"
            , style = MaterialTheme.typography.titleMedium
            )

        })

     }
    )
    {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it), color = MaterialTheme.colorScheme.background)
        {
            val booksViewModel : BooksViewModel=
                viewModel(factory = BooksViewModel.Factory)

            HomeScreen(booksUiState = booksViewModel.booksUiState,retryAction = booksViewModel::getBooks)
        }
    }
}
