package com.group.libraryapp.usecase.book

import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.book.response.BookInventoryResponse
import com.group.libraryapp.exception.fail
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InventoryBookUseCase (
    val bookRepository: BookRepository,
    val userRepository: UserRepository,
){
    @Transactional(readOnly = true)
    fun inventory(id: Long, companyId: Long, page: Int, pageSize: Int): BookInventoryResponse {
        val books = bookRepository.findAllByCompanyId(companyId, PageRequest.of(page, pageSize))
        val user = userRepository.findByIdOrNull(id)?: fail()
        return BookInventoryResponse.of(user,books)
    }
}