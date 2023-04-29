package com.group.libraryapp.service.user

import com.group.libraryapp.EMAIL
import com.group.libraryapp.NAME
import com.group.libraryapp.PASSWORD
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.command.SignUpCommand
import com.group.libraryapp.exception.EmailAlreadyExistsException
import com.group.libraryapp.usecase.user.SignUpUseCase
import com.group.libraryapp.usecase.user.UserService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserUseCaseTest @Autowired constructor(
    private val userService: UserService,
    private val signUpUseCase: SignUpUseCase,
    private val userRepository: UserRepository,
): DescribeSpec({
    describe("사용자의 회원가입시") {
        val (request, user) = signUp(signUpUseCase)
        context("유저 생성 완료"){
            it("요청한 값과 생성된 값을 비교한다.") {
                user.email.email shouldBe EMAIL
                user.name shouldBe NAME
            }
        }

        context("이미 사용중인 이메일이 있다면") {
            signUp(signUpUseCase)
            it("EmailAlreadyExistsException 예외가 발생한다.") {
                shouldThrow<EmailAlreadyExistsException> {
                    signUpUseCase.signUp(request)
                }
            }
        }
    }
}) {
    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        userRepository.deleteAll()
    }
}

private fun signUp(useCase: SignUpUseCase): Pair<SignUpCommand, User> {
    val command = SignUpCommand(EMAIL, PASSWORD, NAME)
    val user = useCase.signUp(command)
    return Pair(command, user)
}

