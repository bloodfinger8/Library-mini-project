package com.group.libraryapp.usecase.group

import com.group.libraryapp.domain.company.CompanyRepository
import com.group.libraryapp.dto.book.response.ListCompanyResponse
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ListCompanyUseCase(
    val companyRepository: CompanyRepository,
) {
    @Transactional(readOnly = true)
    fun list(page: Int, pageSize: Int): ListCompanyResponse {
        return ListCompanyResponse.of(companyRepository.findAll(PageRequest.of(page, pageSize)))
    }
}
