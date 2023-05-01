package com.group.libraryapp.domain.company

import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
class Company(
    val domain: String,
    var numberOfEmployee: Int,
    @Enumerated(EnumType.STRING)
    val status: CompanyStatus,

    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL] , orphanRemoval = true)
    var employees: MutableList<Employee> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
){
    @CreationTimestamp
    lateinit var createdAt: ZonedDateTime
    @UpdateTimestamp
    lateinit var updatedAt: ZonedDateTime

    companion object {
        fun create(domainName: String= "example.com",
                   numberOfEmployee: Int = 0,
                   status: CompanyStatus = CompanyStatus.OPEN,
                   employees: MutableList<Employee> = mutableListOf(),
                   id: Long? = null
        ): Company {
            return Company(domainName, numberOfEmployee, status, employees, id)
        }
    }

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
            throw IllegalArgumentException("Invalid domain")
        }
    }
}