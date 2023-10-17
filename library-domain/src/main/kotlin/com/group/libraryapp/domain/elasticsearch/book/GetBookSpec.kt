package com.group.libraryapp.domain.elasticsearch.book

data class GetBookSpec(
    val title: String,
    val companyId: Long,
    val page: Int,
    val pageSize: Int
)
