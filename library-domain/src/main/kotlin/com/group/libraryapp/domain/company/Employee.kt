package com.group.libraryapp.domain.company

import com.group.libraryapp.domain.TimeInfoEntity
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.type.company.EmployeeStatus
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

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
