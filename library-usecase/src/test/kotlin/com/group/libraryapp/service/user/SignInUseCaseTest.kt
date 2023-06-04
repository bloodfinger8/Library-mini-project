package com.group.libraryapp.service.user

import com.group.libraryapp.COMPANY_DOMAIN
import com.group.libraryapp.COMPANY_ID
import com.group.libraryapp.COMPANY_NAME
import com.group.libraryapp.EMAIL
import com.group.libraryapp.NAME
import com.group.libraryapp.PASSWORD
import com.group.libraryapp.domain.company.CompanyRepository
import com.group.libraryapp.domain.company.factory.CompanyFactory
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.usecase.user.SignInUseCase
import com.group.libraryapp.usecase.user.SignUpUseCase
import com.group.libraryapp.usecase.user.dto.command.SignInCommand
import com.group.libraryapp.usecase.user.dto.command.SignUpCommand
import com.group.libraryapp.usecase.user.dto.response.UserSignInDto
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
    private val companyRepository: CompanyRepository,
) : DescribeSpec({
    describe("사용자 로그인 시도") {
        signUp(signUpUseCase)
        val result = signIn(signInUseCase)
        context("로그인 성공시") {
            it("잘 맞는다.") {
                result.name shouldBe NAME
            }
        }

        context("이메일 또는 패스워드가 틀렸을 경우") {
            signUp(signUpUseCase)
            it("BadCredentialsException 예외가 발생한다.") {
                shouldThrow<BadCredentialsException> {
                    signInUseCase.signIn(SignInCommand("didwodn82@naver.com", "123456"))
                }
            }
        }
    }
}) {
    override suspend fun beforeTest(testCase: TestCase) {
        companyRepository.save(CompanyFactory.create(COMPANY_NAME, COMPANY_DOMAIN, id = COMPANY_ID))
    }

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        userRepository.deleteAll()
    }
}

private fun signUp(useCase: SignUpUseCase) =
    useCase.signUp(SignUpCommand(EMAIL, PASSWORD, NAME, COMPANY_ID))

private fun signIn(useCase: SignInUseCase): UserSignInDto =
    useCase.signIn(SignInCommand(EMAIL, PASSWORD))
