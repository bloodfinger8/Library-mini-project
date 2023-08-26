package com.group.libraryapp.gateway.jpa.company

import com.group.libraryapp.domain.company.Company
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyJpaAdapter : JpaRepository<Company, Long>
