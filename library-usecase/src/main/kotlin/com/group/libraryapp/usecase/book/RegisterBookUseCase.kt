package com.group.libraryapp.usecase.book

import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.book.factory.BookFactory
import com.group.libraryapp.domain.book.type.BookType
import com.group.libraryapp.domain.company.Company
import com.group.libraryapp.domain.company.CompanyRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import com.group.libraryapp.repository.BookQuerydslRepository
import com.group.libraryapp.usecase.book.dto.command.RegisterBookCommand
import com.group.libraryapp.usecase.book.dto.response.BookStatDto
import com.group.libraryapp.usecase.book.dto.response.RegisterBookDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterBookUseCase(
    val bookRepository: BookRepository,
    val bookQuerydslRepository: BookQuerydslRepository,
    val userRepository: UserRepository,
    val userLoanHistoryRepository: UserLoanHistoryRepository,
    val companyRepository: CompanyRepository
) {
    @Transactional
    fun register(req: RegisterBookCommand): RegisterBookDto {
        val company = companyRepository.findByIdOrNull(req.companyId)
        val book = bookRepository.save(bookFactory(req, company))
        return RegisterBookDto.of(book)
    }

    private fun bookFactory(req: RegisterBookCommand, company: Company?) =
        BookFactory.create(
            req.name,
            BookType.COMPUTER,
            req.publisher,
            req.stock,
            req.location,
            company!!
        )

    @Transactional(readOnly = true)
    fun countLoanedBook(): Int {
        return userLoanHistoryRepository.countByStatus(UserLoanStatus.LOANED).toInt()
    }

    @Transactional(readOnly = true)
    fun getStat(): List<BookStatDto> {
        return bookQuerydslRepository.getStat()
    }
}
