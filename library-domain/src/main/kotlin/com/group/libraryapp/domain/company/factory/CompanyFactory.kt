package com.group.libraryapp.domain.company.factory

import com.group.libraryapp.domain.company.Company
import com.group.libraryapp.domain.company.CompanyStatus
import com.group.libraryapp.domain.company.Employee
import java.time.LocalDateTime

class CompanyFactory {
    companion object {
        fun create(
            name: String = "example",
            domain: String = "example.com",
            numberOfEmployee: Int = 0,
            status: CompanyStatus = CompanyStatus.OPEN,
            employees: MutableList<Employee> = mutableListOf(),
            createdAt: LocalDateTime = LocalDateTime.now(),
            id: Long? = null
        ): Company {
            return Company(name, domain, numberOfEmployee, status, employees, createdAt, id)
        }
    }
}
