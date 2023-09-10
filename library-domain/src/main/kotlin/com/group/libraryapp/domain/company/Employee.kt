package com.group.libraryapp.domain.company

import com.group.libraryapp.domain.TimeInfoEntity
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.type.company.EmployeeStatus
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne

@Entity
class Employee(
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: User,

    @ManyToOne
    val company: Company,

    @Enumerated(EnumType.STRING)
    val status: EmployeeStatus,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) : TimeInfoEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Employee
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0

    companion object {
        fun create(
            user: User,
            company: Company,
            status: EmployeeStatus,
            id: Long? = null
        ): Employee {
            return Employee(user, company, status, id)
        }
    }
}
