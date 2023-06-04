package com.group.libraryapp.domain.company

import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.exception.invalidEmailFail
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Company(
    val name: String,

    val domain: String,

    var numberOfEmployee: Int,

    @Enumerated(EnumType.STRING)
    val status: CompanyStatus,

    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL], orphanRemoval = true)
    var employees: MutableList<Employee> = mutableListOf(),

    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) {
    var updatedAt: LocalDateTime = createdAt

    private fun increaseNumberOfEmployee() {
        this.numberOfEmployee += 1
    }

    fun join(user: User): Company {
        domainCheck(user.email)
        increaseNumberOfEmployee()
        this.employees.add(Employee.create(user, this, EmployeeStatus.EMPLOYMENT))
        return this
    }

    private fun domainCheck(email: Email) {
        if (email.domain != this.domain) {
            invalidEmailFail(email.domain)
        }
    }
}
