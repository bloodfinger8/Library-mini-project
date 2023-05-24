package com.group.libraryapp.usecase.group

import com.group.libraryapp.domain.company.CompanyRepository
import com.group.libraryapp.domain.company.factory.CompanyFactory
import com.group.libraryapp.usecase.book.dto.command.RegisterCompanyCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterCompanyUseCase(
    val companyRepository: CompanyRepository,
) {
    @Transactional
    fun register(command: RegisterCompanyCommand) {
        companyRepository.save(CompanyFactory.create(command.name, command.domain))
    }
}
