package com.group.libraryapp.service.user

import com.group.libraryapp.EMAIL
import com.group.libraryapp.NAME
import com.group.libraryapp.PASSWORD
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserSignInRequest
import com.group.libraryapp.dto.user.response.UserSignInResponse
import com.group.libraryapp.usecase.user.UserService
import com.group.libraryapp.usecase.user.UserSignInUseCase
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.BadCredentialsException

@SpringBootTest
class SignInUseCaseTest(
    private val userService: UserService,
    private val userSignInUseCase: UserSignInUseCase,
    private val userRepository: UserRepository,
): DescribeSpec({
    describe("사용자 로그인 시도") {
        signUp(userService)
        val result = signIn(userSignInUseCase)
        context("로그인 성공시") {
            it("잘 맞는다.") {
                result.name shouldBe NAME
            }
        }

        context("이메일 또는 패스워드가 틀렸을 경우"){
            signUp(userService)
            it("BadCredentialsException 예외가 발생한다.") {
                shouldThrow<BadCredentialsException> {
                    userSignInUseCase.signIn(UserSignInRequest("didwodn82@naver.com","123456"))
                }
            }
        }
    }
}){
    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        userRepository.deleteAll()
    }
}

private fun signUp(userService: UserService) =
    userService.signUp(UserCreateRequest(EMAIL, PASSWORD, NAME))

private fun signIn(userSignInUseCase: UserSignInUseCase): UserSignInResponse =
    userSignInUseCase.signIn(UserSignInRequest(EMAIL, PASSWORD))