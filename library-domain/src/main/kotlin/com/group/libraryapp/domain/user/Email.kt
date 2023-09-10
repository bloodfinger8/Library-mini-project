package com.group.libraryapp.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.util.regex.Pattern

@Embeddable
class Email(
    @Column(length = 80)
    val mailId: String,
    @Column(length = 80)
    val domain: String
) {
    constructor(username: String) : this(username.split('@')[0], username.split('@')[1])

    init {
        if (!isValidEmail()) throw IllegalArgumentException("이메일 형식이 올바르지 않습니다.")
    }

    fun name(): String {
        return "$mailId@$domain"
    }

    private fun isValidEmail(): Boolean {
        return EMAIL_REGEX.matcher(name()).find()
    }

    companion object {
        private val EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
    }
}
