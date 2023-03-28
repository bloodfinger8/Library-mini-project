package com.group.libraryapp.service.book

import com.group.libraryapp.domain.user.UserRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest
class BookConcurrencyTest @Autowired constructor(
    private val userRepository: UserRepository,
): BehaviorSpec({
})