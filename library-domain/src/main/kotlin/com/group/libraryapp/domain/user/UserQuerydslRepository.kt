package com.group.libraryapp.domain.user

interface UserQuerydslRepository {
    fun getHistories(): List<User>
}
