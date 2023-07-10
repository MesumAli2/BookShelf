package com.example.bookshelf

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.ui.input.key.Key

import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf.ui.screens.BooksViewModel
import com.example.bookshelf.ui.screens.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksApp() {
    val booksViewModel: BooksViewModel = viewModel(factory = BooksViewModel.Factory)

    // Create a mutable state for storing the search query
    var searchQuery by remember { mutableStateOf("") }
    // Create a mutable state for indicating whether the search view is expanded or not
    var isSearchExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Book Shelf",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                actions = {
                    if (isSearchExpanded) {
                        // Show the search view
                        SearchView(
                            searchQuery = searchQuery,
                            onQueryChange = { query ->
                                searchQuery = query
                                // Call a search function or perform search operations here
                                performSearch(query, booksViewModel)
                            },
                            onClose = { isSearchExpanded = false }
                        , booksViewModel = booksViewModel
                        )
                    } else {
                        // Show the search button
                        IconButton(
                            onClick = { isSearchExpanded = true },
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                    }
                }
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreen(
                booksUiState = booksViewModel.booksUiState,
                retryAction = booksViewModel::getBooks
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onClose: () -> Unit,
    booksViewModel: BooksViewModel
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onClose() },
            modifier = Modifier.padding(start = 16.dp, end = 8.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        TextField(
            value = searchQuery,
            onValueChange = { query -> onQueryChange(query) },
            placeholder = { Text("Search") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .focusRequester(focusRequester)
                .background(Color.Transparent)
                .onPreviewKeyEvent { event ->
                    if (event.key == Key.Enter && event.type == KeyEventType.KeyUp) {
                        // Call a search function or perform search operations here
                        performSearch(searchQuery, booksViewModel =booksViewModel )
                        keyboardController?.hide()

                        true
                    } else {
                        false
                    }
                }
        )
    }
}



fun performSearch(query: String, booksViewModel: BooksViewModel) {

    // Perform search operations based on the query
    // Update the UI with the search results
    Log.d("QuerySearch", query.toString())
    booksViewModel.getBooks(query)
}

