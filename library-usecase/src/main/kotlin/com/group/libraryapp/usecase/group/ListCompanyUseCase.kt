package com.group.libraryapp.usecase.group

import com.group.libraryapp.domain.company.CompanyRepository
import com.group.libraryapp.SliceDto
import com.group.libraryapp.usecase.book.dto.response.CompanyDto
import com.group.libraryapp.usecase.group.assembler.CompanyAssembler
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ListCompanyUseCase(
    private val companyRepository: CompanyRepository,
    private val companyAssembler: CompanyAssembler
) {
    @Transactional(readOnly = true)
    fun list(page: Int, pageSize: Int): SliceDto<CompanyDto> {
        val companies = companyRepository.findAll(PageRequest.of(page, pageSize))
        return SliceDto(companies.hasNext(), companyAssembler.toDtoList(companies.content))
    }
}
