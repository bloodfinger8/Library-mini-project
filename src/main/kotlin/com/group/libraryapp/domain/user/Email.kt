package com.group.libraryapp.domain.user

import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Email (
    @Column(length = 80)
    var email: String
){
    init {

    }

    companion object{
        private val EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
    }
}