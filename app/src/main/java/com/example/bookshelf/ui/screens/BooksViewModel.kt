package com.example.bookshelf.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BooksApplication
import com.example.bookshelf.data.BooksRepository
import com.example.bookshelf.model.BooksRp
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface BooksUiState {
    data class Success(val booksRp: BooksRp) : BooksUiState
    object Error : BooksUiState
    object Loading : BooksUiState
}
class BooksViewModel(private val booksRepository: BooksRepository) : ViewModel(){

    var booksUiState : BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set

    init {
        getBooks()
    }

    fun getBooks(query: String= "Science") {
        viewModelScope.launch {
            booksUiState = BooksUiState.Loading
            booksUiState = try {
               BooksUiState.Success(booksRepository.getBooks(query))
            }catch (E : IOException){
                BooksUiState.Error
            }
            catch (E : HttpException){
                BooksUiState.Error
            }
        }
    }

    companion object{
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as BooksApplication)
                val booksRepository = application.container.booksRepository
                BooksViewModel(booksRepository = booksRepository)
            }
        }
    }
}