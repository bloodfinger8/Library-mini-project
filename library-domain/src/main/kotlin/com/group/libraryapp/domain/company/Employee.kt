package com.group.libraryapp.domain.company

import com.group.libraryapp.domain.user.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.ZonedDateTime
import javax.persistence.*

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
) {
    @CreationTimestamp
    lateinit var createdAt: ZonedDateTime

    @UpdateTimestamp
    lateinit var updatedAt: ZonedDateTime

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
