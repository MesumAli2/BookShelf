package com.example.bookshelf.model

data class VolumeInfo(
    val allowAnonLogging: Boolean?,
    val averageRating: Double?,
    val canonicalVolumeLink: String?,
    val contentVersion: String?,
    val description: String?,
    val imageLinks: ImageLinks?,
    val infoLink: String?,
    val language: String?,
    val maturityRating: String?,
    val pageCount: Int?,
    val panelizationSummary: PanelizationSummary?,
    val previewLink: String?,
    val printType: String?,
    val publishedDate: String?,
    val ratingsCount: Int?,
    val readingModes: ReadingModes?,
    val title: String?
)