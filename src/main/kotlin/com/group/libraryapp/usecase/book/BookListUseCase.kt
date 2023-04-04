package com.group.libraryapp.usecase.book

import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.book.response.BookListResponse
import com.group.libraryapp.security.AuthenticationDTO
import com.group.libraryapp.usecase.UserLoanHistoryCollection
import com.group.libraryapp.util.fail
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookListUseCase (
    val bookRepository: BookRepository,
    val userRepository: UserRepository,
    val userLoanHistoryRepository: UserLoanHistoryRepository,
){
    @Transactional(readOnly = true)
    fun list(authenticationDTO: AuthenticationDTO): List<BookListResponse> {
        val books = bookRepository.findAll()
        val user = userRepository.findByIdOrNull(authenticationDTO.id)?: fail()

        return books.map { book -> BookListResponse(book.id!!,book.name,book.type,book.publisher,book.stock) }
    }
}