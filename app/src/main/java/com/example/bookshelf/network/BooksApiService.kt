package com.example.bookshelf.network

import com.example.bookshelf.model.BooksRp
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApiService {
    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query: String
    ): BooksRp
}
