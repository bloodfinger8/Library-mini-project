package com.group.libraryapp.usecase.book

import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.book.response.BookInventoryResponse
import com.group.libraryapp.security.AuthenticationDTO
import com.group.libraryapp.util.fail
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookInventoryUseCase (
    val bookRepository: BookRepository,
    val userRepository: UserRepository,
){
    @Transactional(readOnly = true)
    fun inventory(authenticationDTO: AuthenticationDTO): BookInventoryResponse {
        val books = bookRepository.findAll()
        val user = userRepository.findByIdOrNull(authenticationDTO.id)?: fail()

        return BookInventoryResponse.of(user,books)
    }
}