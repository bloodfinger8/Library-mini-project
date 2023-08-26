package com.group.libraryapp.domain // ktlint-disable filename

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PreUpdate

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
