package com.group.libraryapp.domain.book

interface BookQuerydslRepository {
    fun getStat(): List<BookStatDto>
}
