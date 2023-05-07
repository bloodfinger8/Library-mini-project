package com.group.libraryapp.service.user

import com.group.libraryapp.*
import com.group.libraryapp.domain.company.Company
import com.group.libraryapp.domain.company.CompanyRepository
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.command.SignInCommand
import com.group.libraryapp.dto.user.command.SignUpCommand
import com.group.libraryapp.dto.user.response.SignUpResponse
import com.group.libraryapp.exception.EmailAlreadyExistsException
import com.group.libraryapp.exception.InvalidEmailDomainException
import com.group.libraryapp.usecase.user.SignUpUseCase
import com.group.libraryapp.usecase.user.UserService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.BadCredentialsException

@SpringBootTest
class UserUseCaseTest @Autowired constructor(
    private val signUpUseCase: SignUpUseCase,
    private val userRepository: UserRepository,
    private val companyRepository: CompanyRepository,
): DescribeSpec({
    describe("사용자의 회원가입시") {
        val (request, response) = signUp(signUpUseCase)
        context("유저 생성 완료"){
            it("요청한 값과 생성된 값을 비교한다.") {
                response.email shouldBe EMAIL
                response.name shouldBe NAME
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

        context("회사 도메인과 가입 이메일이 일치하지 않는 경우") {
            it("InvalidEmailDomainException 예외가 발생한다.") {
                shouldThrow<InvalidEmailDomainException> {
                    signUpUseCase.signUp(SignUpCommand(INVALID_EMAIL, PASSWORD, NAME, COMPANY_ID))
                }
            }
        }
    }
}) {
    override suspend fun beforeTest(testCase: TestCase) {
        companyRepository.save(Company.create(COMPANY_NAME, COMPANY_DOMAIN, id = COMPANY_ID))
    }

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        userRepository.deleteAll()
    }
}

private fun signUp(useCase: SignUpUseCase): Pair<SignUpCommand, SignUpResponse> {
    val command = SignUpCommand(EMAIL, PASSWORD, NAME, COMPANY_ID)
    val response = useCase.signUp(command)
    return Pair(command, response)
}

