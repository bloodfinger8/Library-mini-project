package com.group.libraryapp.usecase.group.assembler

import com.group.libraryapp.domain.company.Company
import com.group.libraryapp.usecase.book.dto.response.CompanyDto
import org.springframework.stereotype.Component

@Component
class CompanyAssembler {
    fun toDtoList(companies: List<Company>): List<CompanyDto> {
        return companies.map { CompanyDto.of(it) }
    }
}
