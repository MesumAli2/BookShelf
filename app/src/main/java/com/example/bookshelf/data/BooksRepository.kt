package com.example.bookshelf.data

import com.example.bookshelf.model.BooksRp
import com.example.bookshelf.network.BooksApiService

interface BooksRepository {
    suspend fun getBooks(query: String): BooksRp
}

class DefaultBooksRepository(
    private val booksApiService : BooksApiService
) : BooksRepository{
    override suspend fun getBooks(query: String ): BooksRp {
        return booksApiService.getBooks(query)
    }

}