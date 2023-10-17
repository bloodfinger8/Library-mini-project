package com.group.libraryapp.usecase.search

import com.group.libraryapp.SliceDto
import com.group.libraryapp.domain.elasticsearch.book.BookSearcher
import com.group.libraryapp.domain.elasticsearch.book.GetBookSpec
import com.group.libraryapp.usecase.book.dto.response.BookDto
import com.group.libraryapp.usecase.search.dto.SearchBookCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SearchBookUseCase(
    private val bookSearcher: BookSearcher
) {

    @Transactional(readOnly = true)
    fun get(cmd: SearchBookCommand): SliceDto<BookDto> {
        val bookResult = bookSearcher.search(GetBookSpec(cmd.title, cmd.companyId, cmd.page, cmd.pageSize))
        return SliceDto(
            bookResult.hasNext,
            bookResult.elements.map { BookDto.of(it) }
        )
    }
}
