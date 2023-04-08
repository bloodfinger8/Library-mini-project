package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.exception.EmailAlreadyExistsException
import com.group.libraryapp.usecase.user.UserService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.startWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserUseCaseTest @Autowired constructor(
    private val userService: UserService,
    private val userRepository: UserRepository,

): BehaviorSpec({

    Given("사용자의 회원가입시") {
        val request = UserCreateRequest("didwodn82@naver.com","12345" , "양재우")
        When("유저 생성이 잘된다."){
            val user = userService.signUp(request)
            then("요청한 값과 생성된 값을 비교한다.") {
                user.email.email shouldBe "didwodn82@naver.com"
                user.name shouldBe "양재우"
            }
        }

        When("이미 사용중인 이메일이 있다면") {
            userRepository.save(User.create(Email("didwodn82@naver.com"),"12345","양재우"))
            val exception = shouldThrow<EmailAlreadyExistsException> {
                userService.signUp(request)
            }
            then("예외가 발생한다.") {
                exception.code shouldBe 40403
            }
        }

    }

})

