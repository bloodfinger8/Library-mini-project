package com.group.libraryapp.domain // ktlint-disable filename

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PreUpdate
import java.time.LocalDateTime

@MappedSuperclass
abstract class TimeInfoEntity(
    @Column(name = "created_at")
    open val createdAt: LocalDateTime = LocalDateTime.now()
) {
    @Column(name = "updated_at")
    private var updatedAt: LocalDateTime = createdAt

    @PreUpdate
    fun updated() {
        updatedAt = LocalDateTime.now()
    }

    fun getUpdatedAt(): LocalDateTime {
        return updatedAt
    }
}
