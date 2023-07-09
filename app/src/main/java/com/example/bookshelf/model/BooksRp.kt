package com.example.bookshelf.model

data class BooksRp(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)