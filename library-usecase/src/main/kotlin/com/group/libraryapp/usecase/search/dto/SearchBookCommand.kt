package com.group.libraryapp.usecase.search.dto

data class SearchBookCommand(
    val title: String,
    val companyId: Long,
    val page: Int,
    val pageSize: Int
)
