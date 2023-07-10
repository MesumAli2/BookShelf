package com.example.bookshelf.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.model.Item

@Composable
    fun HomeScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    booksUiState: BooksUiState
){

when(booksUiState){
    is BooksUiState.Loading-> {
        LoadingScreen(modifier = modifier.fillMaxSize())

    }
    is BooksUiState.Success ->{
        BooksListScreen(booksUiState.booksRp.items, modifier = modifier.fillMaxSize())
    }
    
    is BooksUiState.Error ->{
    ErrorScreen(modifier = modifier.fillMaxSize(), retryAction = retryAction)
    }
    }

    }

@Composable
fun ErrorScreen(modifier: Modifier, retryAction: () -> Unit) {
Column(modifier = modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

    Image(painter = painterResource(id = R.drawable.ic_connection_error), contentDescription ="" )
    Text(text = stringResource(id = R.string.loading_failed))
    Button(onClick = retryAction) {
        Text(text = stringResource(id = R.string.retry))

    }
}
}

@Composable
fun LoadingScreen(modifier: Modifier) {
    Image(painter = painterResource(id = R.drawable.loading_img), contentDescription ="", modifier = modifier )

}

//@Composable
//fun BooksListScreen(books: List<Item>, modifier: Modifier) {
//
//    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier =modifier, contentPadding = PaddingValues(4.dp) ){
//        items(items = books, key = { book -> book.id }) { book ->
//        val minHeight = 200.dp // Set the minimum height for the card
//        val maxHeight = 300.dp // Set the maximum height for the card
//        val cardHeight = lerp(minHeight, maxHeight, book.id.hashCode().toFloat() / Int.MAX_VALUE)
//        //BooksPhotoCard(book = book, modifier = Modifier.height(cardHeight))
//            BooksPhotoCard(book = book, modifier = Modifier.fillMaxSize())
//    }
//    }
//    }
//@Composable
//fun BooksListScreen(books: List<Item>, modifier: Modifier) {
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(2),
//        modifier = modifier,
//        contentPadding = PaddingValues(4.dp)
//    ) {
//        itemsIndexed(books) { index, book ->
//            val cardHeight = remember(book.id) {
//                if (index % 3 == 0) {
//                    300.dp // Set taller height for every third item
//                } else {
//                    200.dp // Set regular height for other items
//                }
//            }
//            BooksPhotoCard(book = book, modifier = Modifier.height(cardHeight))
//        }
//    }
//}

@Composable
fun BooksListScreen(books: List<Item>, modifier: Modifier) {
    val columnCount = 2 // Number of columns in the grid
    val spacing = 8.dp // Spacing between chunked items
    val padding = 4.dp // Padding around each item
    val groupedItems = books.chunked(columnCount) // Group items into pairs


    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount),
        modifier = modifier,
        contentPadding = PaddingValues(spacing)
    ) {
        items(groupedItems.size) { index ->
            Column(Modifier.fillMaxWidth()) {
                groupedItems[index].forEachIndexed { itemIndex, book ->
                    val cardHeight = if (book.id.hashCode() % 3 == 0) {
                        300.dp // Set taller height for certain items
                    } else {
                        200.dp // Set regular height for other items
                    }
                    BooksPhotoCard(
                        book = book,
                        modifier = Modifier
                            .height(cardHeight)
                            .padding(padding)
                    )

                    // Add spacing between chunked items
                    if (itemIndex < groupedItems[index].size - 1) {
                        Spacer(modifier = Modifier.height(spacing))
                    }
                }
            }
        }
    }
}










@Composable
fun BooksPhotoCard(book: Item, modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxHeight()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(book.volumeInfo.imageLinks.thumbnail.replace("http://", "https://"))
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img),
            contentDescription = "${book.id}",
            modifier = Modifier.fillMaxWidth()
        )
    }
}
