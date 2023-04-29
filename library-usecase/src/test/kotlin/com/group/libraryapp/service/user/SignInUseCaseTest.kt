package com.group.libraryapp.service.user

import com.group.libraryapp.EMAIL
import com.group.libraryapp.NAME
import com.group.libraryapp.PASSWORD
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.command.SignInCommand
import com.group.libraryapp.dto.user.command.SignUpCommand
import com.group.libraryapp.dto.user.response.UserSignInResponse
import com.group.libraryapp.usecase.user.SignInUseCase
import com.group.libraryapp.usecase.user.SignUpUseCase
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.BadCredentialsException

@SpringBootTest
class SignInUseCaseTest(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val userRepository: UserRepository,
): DescribeSpec({
    describe("사용자 로그인 시도") {
        signUp(signUpUseCase)
        val result = signIn(signInUseCase)
        context("로그인 성공시") {
            it("잘 맞는다.") {
                result.name shouldBe NAME
            }
        }

        context("이메일 또는 패스워드가 틀렸을 경우"){
            signUp(signUpUseCase)
            it("BadCredentialsException 예외가 발생한다.") {
                shouldThrow<BadCredentialsException> {
                    signInUseCase.signIn(SignInCommand("didwodn82@naver.com","123456"))
                }
            }
        }
    }
}){
    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        userRepository.deleteAll()
    }
}

private fun signUp(useCase: SignUpUseCase) =
    useCase.signUp(SignUpCommand(EMAIL, PASSWORD, NAME))

private fun signIn(useCase: SignInUseCase): UserSignInResponse =
    useCase.signIn(SignInCommand(EMAIL, PASSWORD))