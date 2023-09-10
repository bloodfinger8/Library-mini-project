package com.group.libraryapp.domain.company

import com.group.libraryapp.domain.TimeInfoEntity
import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.exception.invalidEmailFail
import com.group.libraryapp.type.company.CompanyStatus
import com.group.libraryapp.type.company.EmployeeStatus
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class Company(
    val name: String,

    val domain: String,

    var numberOfEmployee: Int,

    @Enumerated(EnumType.STRING)
    val status: CompanyStatus,

    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL], orphanRemoval = true)
    var employees: MutableList<Employee> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) : TimeInfoEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Company
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0

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
