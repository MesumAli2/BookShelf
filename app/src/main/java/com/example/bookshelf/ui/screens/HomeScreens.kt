package com.example.bookshelf.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.model.Item

@Composable
    fun HomeScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    booksUiState: BooksUiState
){

when(booksUiState){
    is BooksUiState.Loading-> {

    }
    is BooksUiState.Success ->{
        BooksListScreen(booksUiState.booksRp.items, modifier = modifier.fillMaxSize())
    }
}

    }

@Composable
fun BooksListScreen(books: List<Item>, modifier: Modifier) {

    LazyVerticalGrid(columns = GridCells.Adaptive((200.dp)), modifier =modifier, contentPadding = PaddingValues(4.dp) ){
        items(items = books, key = {books -> books.id}){
            book -> BooksPhotoCard(book = book, modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1.5f))
        }
    }

}

@Composable
fun BooksPhotoCard(book: Item, modifier: Modifier) {
    Card(modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(book.volumeInfo.imageLinks.thumbnail)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.)
        )

    }

}
