package com.group.libraryapp.domain.company

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
class Company(
    val domainName: String,
    val numberOfEmployee: Int,
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
        fun create(domainName: String,
                   numberOfEmployee: Int = 1,
                   status: CompanyStatus = CompanyStatus.OPEN,
                   employees: MutableList<Employee> = mutableListOf(),
                   id: Long? = null
        ): Company {
            return Company(domainName, numberOfEmployee, status, employees, id)
        }
    }
}